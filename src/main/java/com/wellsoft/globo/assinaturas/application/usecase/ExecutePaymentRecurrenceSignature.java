package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.application.dto.CreateRecurrenceDto;
import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.domain.service.RecurrenceService;
import com.wellsoft.globo.assinaturas.domain.service.SignatureService;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.PayCreditCardRequest;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExecutePaymentRecurrenceSignature {

    private final UserProvider userProvider;
    private final SignatureService signatureService;
    private final RecurrenceService recurrenceService;


    @Transactional
    public void apply(String userIdentifier, BigDecimal value){
        log.info("Execute Payment Recurrence Signature to User: {}", userIdentifier);

        var user = userProvider.findUserByIdentifier(userIdentifier);

        var createPaymentResponse = signatureService.createPaymentInAsaas(user.getClientCustomerId(), BillingType.CREDIT_CARD, value, LocalDate.now().toString());

        var paymentResponse = signatureService.payWithCreditCard(createPaymentResponse.id, PayCreditCardRequest.builder()
                .creditCardToken(user.getCreditCardDbo().getCreditCardToken())
                .build());

        var payment = signatureService.savePayment(paymentResponse);

        if(!PaymentStatus.CONFIRMED.equals(paymentResponse.status)){
            inactiveSubscriptionAndRetry(user, payment, value);
        } else {
            renewSubscription(payment, user.getSignatureDbo());
            createNewPaymentRecurrence(value, user);
        }

    }

    private void createNewPaymentRecurrence(BigDecimal value, UserDbo user) {
        log.info("Create New Payment Recurrence");

        recurrenceService.sendCreatePaymentRecurrence(CreateRecurrenceDto.builder()
                .userIdentifier(user.getUserIdentifier())
                .billingType(BillingType.CREDIT_CARD)
                .value(value)
                .recurrenceDate(LocalDateTime.now().plusMonths(1))
                .build());
    }

    private void renewSubscription(PaymentsDbo payment, SignatureDbo signature) {
        log.info("Renew Subscription");
        payment.setSignatureDbo(signature);

        signature.setStatus(SignatureStatus.ATIVA);
        signature.setExpirationDate(LocalDateTime.now().plusMonths(1));
        signature.addPayment(payment);
    }

    private void inactiveSubscriptionAndRetry(UserDbo user, PaymentsDbo payment, BigDecimal value){
        log.info("Inactive Subscription And Scheduler Retry");
        var signature = user.getSignatureDbo();
        var retryCount = signature.getRetryPayment();

        payment.setSignatureDbo(signature);
        signature.addPayment(payment);

        if(retryCount != null && retryCount <= 3){
            signature.setRetryPayment(retryCount + 1);
            recurrenceService.sendCreatePaymentRecurrence(CreateRecurrenceDto.builder()
                    .billingType(BillingType.CREDIT_CARD)
                    .userIdentifier(user.getUserIdentifier())
                    .value(value)
                    .recurrenceDate(LocalDateTime.now().plusDays(1))
                    .build());
        } else {
            signature.setStatus(SignatureStatus.INATIVA);
        }

    }
}
