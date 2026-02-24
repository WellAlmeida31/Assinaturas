package com.wellsoft.globo.assinaturas.domain.service;

import com.wellsoft.globo.assinaturas.domain.provider.PaymentProvider;
import com.wellsoft.globo.assinaturas.domain.provider.SignatureProvider;
import com.wellsoft.globo.assinaturas.infrastructure.client.AsaasPaymentClient;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.PayCreditCardRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.PaymentRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.TokenizeCreditCardRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.CreatePaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.PaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.TokenizeCreditCardResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.BillingType;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.PaymentsDbo;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureDbo;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.PaymentMapper;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.SignatureMapper;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.SignatureRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignatureService {

    private final SignatureProvider signatureProvider;
    private final PaymentProvider paymentProvider;
    private final AsaasPaymentClient paymentClient;
    private final SignatureMapper signatureMapper;
    private final PaymentMapper paymentMapper;

    public SignatureDbo createInitialSignature(SignatureRequestDto dto){
        log.info("Creating Signature by Identifier: {}", dto.identifier());
        return signatureProvider.createSignature(signatureMapper.toSignatureDbo(dto));
    }

    public CreatePaymentResponse createPaymentInAsaas(String customer, BillingType billingType, BigDecimal value, String dueDate){
        log.info("Creating payment");
        return paymentClient.createPayment(PaymentRequest.builder()
                .billingType(billingType)
                .customer(customer)
                .dueDate(dueDate)
                .value(value)
                .build());
    }

    public PaymentsDbo savePayment(PaymentResponse paymentResponse){
        log.info("Saving payment");
        return paymentProvider.createPayment(paymentMapper.toPaymentDbo(paymentResponse));
    }

    public TokenizeCreditCardResponse tokenizedCreditCard(TokenizeCreditCardRequest request){
        log.info("Start tokenized credit card");
        return paymentClient.tokenizeCreditCard(request);
    }

    public PaymentResponse payWithCreditCard(String paymentId, PayCreditCardRequest creditCardRequest){
        log.info("Start pay with credit card");
        return paymentClient.payWithCreditCard(paymentId, creditCardRequest);
    }
}
