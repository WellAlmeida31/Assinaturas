package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.application.dto.CreateRecurrenceDto;
import com.wellsoft.globo.assinaturas.domain.exception.AlreadySubscription;
import com.wellsoft.globo.assinaturas.domain.exception.PaymentFailedException;
import com.wellsoft.globo.assinaturas.domain.service.CreditCardService;
import com.wellsoft.globo.assinaturas.domain.service.RecurrenceService;
import com.wellsoft.globo.assinaturas.domain.service.SignatureService;
import com.wellsoft.globo.assinaturas.domain.service.UserService;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.CreditCard;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.CreditCardHolderInfo;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.PayCreditCardRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.TokenizeCreditCardRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.CreatePaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.PaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.TokenizeCreditCardResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.*;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.SignatureRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.SignatureResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateSignatureAndPaymentUseCase {

    private final SignatureService signatureService;
    private final CreditCardService creditCardService;
    private final RecurrenceService recurrenceService;
    private final UserService userService;

    @Transactional
    public SignatureResponseDto createAndPayment(SignatureRequestDto dto){
        var user = userService.findUserByIdentifier(dto.identifier());
        if(nonNull(user.getSignatureDbo())){
            throw new AlreadySubscription("The user already has a subscription.");
        }
        var signature = signatureService.createInitialSignature(dto);
        user.setSignatureDbo(signature);

        var planValue = signatureService.getPlanValue(dto.plan());
        var createPaymentResponse = signatureService.createPaymentInAsaas(user.getClientCustomerId(), BillingType.CREDIT_CARD, planValue, LocalDate.now().toString());
        var tokenizedCard = getTokenizeCreditCard(dto, user);
        var paymentResponse = makePayment(createPaymentResponse, tokenizedCard);

        var payment = signatureService.savePayment(paymentResponse);

        activateSubscription(payment, signature);

        var creditCard = registerCreditCard(dto, tokenizedCard, user);

        recurrenceService.sendCreatePaymentRecurrence(CreateRecurrenceDto.builder()
                .userIdentifier(user.getUserIdentifier())
                .billingType(BillingType.CREDIT_CARD)
                .value(planValue)
                .recurrenceDate(LocalDateTime.now().plusMinutes(1))
//                .recurrenceDate(LocalDateTime.now().plusMonths(1))
                .build());

        return SignatureResponseDto.builder()
                .identifier(user.getUserIdentifier())
                .plan(signature.getPlan())
                .startDate(signature.getStartDate())
                .expirationDate(signature.getExpirationDate())
                .holderName(creditCard.getHolderName())
                .number(creditCard.getNumber())
                .expiryMonth(creditCard.getExpiryMonth())
                .expiryYear(creditCard.getExpiryYear())
                .ccv(creditCard.getCcv())
                .creditCardBrand(creditCard.getCreditCardBrand())
                .postalCode(creditCard.getPostalCode())
                .addressHolderNumber(creditCard.getAddressHolderNumber())
                .taxId(creditCard.getTaxId())
                .phone(creditCard.getPhone())
                .email(creditCard.getEmail())
                .transactionReceiptUrl(paymentResponse.transactionReceiptUrl)
                .build();
    }

    private CreditCardDbo registerCreditCard(SignatureRequestDto dto, TokenizeCreditCardResponse tokenizedCard, UserDbo user) {
        var creditCard = creditCardService.saveCreditCardWithToken(dto);
        creditCard.setCreditCardBrand(tokenizedCard.getCreditCardBrand());
        creditCard.setCreditCardToken(tokenizedCard.getCreditCardToken());
        creditCard.setUserDbo(user);
        user.setCreditCardDbo(creditCard);
        return creditCard;
    }

    private void activateSubscription(PaymentsDbo payment, SignatureDbo signature) {
        payment.setSignatureDbo(signature);

        signature.setStatus(SignatureStatus.ATIVA);
        signature.setStartDate(LocalDateTime.now());
        signature.setExpirationDate(LocalDateTime.now().plusMonths(1));
        signature.addPayment(payment);
    }

    private PaymentResponse makePayment(CreatePaymentResponse createPaymentResponse, TokenizeCreditCardResponse tokenizedCard) {
        var paymentResponse = signatureService.payWithCreditCard(createPaymentResponse.id, PayCreditCardRequest.builder()
                .creditCardToken(tokenizedCard.getCreditCardToken())
                .build());
        if(!PaymentStatus.CONFIRMED.equals(paymentResponse.status)){
            throw new PaymentFailedException("Payment Not Confirmed");
        }
        return paymentResponse;
    }

    private TokenizeCreditCardResponse getTokenizeCreditCard(SignatureRequestDto dto, UserDbo user) {
        return signatureService.tokenizedCreditCard(TokenizeCreditCardRequest.builder()
                .creditCard(CreditCard.builder()
                        .number(dto.number())
                        .expiryMonth(dto.expiryMonth())
                        .expiryYear(dto.expiryYear())
                        .ccv(dto.ccv())
                        .holderName(dto.holderName())
                        .build())
                .creditCardHolderInfo(CreditCardHolderInfo.builder()
                        .addressNumber(dto.addressHolderNumber())
                        .email(dto.email())
                        .name(dto.holderName())
                        .phone(dto.phone())
                        .cpfCnpj(dto.taxId())
                        .postalCode(dto.postalCode())
                        .build())
                .customer(user.getClientCustomerId())
                .remoteIp(dto.remoteIp())
                .build());
    }
}
