package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.domain.service.RecurrenceService;
import com.wellsoft.globo.assinaturas.domain.service.SignatureService;
import com.wellsoft.globo.assinaturas.domain.service.UserService;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.CreatePaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.PaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExecutePaymentRecurrenceSignatureUseCaseTest {

    @Mock
    private UserService userService;

    @Mock
    private SignatureService signatureService;

    @Mock
    private RecurrenceService recurrenceService;

    @InjectMocks
    private ExecutePaymentRecurrenceSignatureUseCase useCase;

    private UserDbo user;
    private SignatureDbo signature;
    private PaymentsDbo payment;
    private BigDecimal value;

    @BeforeEach
    void setup() {

        value = BigDecimal.valueOf(59.90);

        signature = new SignatureDbo();
        signature.setStatus(SignatureStatus.ATIVA);
        signature.setRetryPayment(0);

        payment = new PaymentsDbo();

        var creditCard = new CreditCardDbo();
        creditCard.setCreditCardToken("token-123");

        user = UserDbo.builder()
                .userIdentifier("user-123")
                .clientCustomerId("cust-123")
                .build();
        user.setCreditCardDbo(creditCard);
        user.setSignatureDbo(signature);
    }

    @Test
    void shouldRenewSubscriptionWhenPaymentConfirmed() {

        var createPaymentResponse = new CreatePaymentResponse();
        createPaymentResponse.id = "payment-id";

        var paymentResponse = new PaymentResponse();
        paymentResponse.status = PaymentStatus.CONFIRMED;

        when(userService.findUserByIdentifier("user-123"))
                .thenReturn(user);

        when(signatureService.createPaymentInAsaas(any(), any(), any(), any()))
                .thenReturn(createPaymentResponse);

        when(signatureService.payWithCreditCard(any(), any()))
                .thenReturn(paymentResponse);

        when(signatureService.savePayment(paymentResponse))
                .thenReturn(payment);

        useCase.apply("user-123", value);

        assertEquals(SignatureStatus.ATIVA, signature.getStatus());
        assertNotNull(signature.getExpirationDate());

        verify(recurrenceService, times(1))
                .sendCreatePaymentRecurrence(any());

        assertEquals(signature, payment.getSignatureDbo());
    }

    @Test
    void shouldRetryWhenPaymentFailsAndRetryLessThanThree() {

        signature.setRetryPayment(1);

        var createPaymentResponse = new CreatePaymentResponse();
        createPaymentResponse.id = "payment-id";

        var paymentResponse = new PaymentResponse();
        paymentResponse.status = PaymentStatus.PENDING;

        when(userService.findUserByIdentifier("user-123"))
                .thenReturn(user);

        when(signatureService.createPaymentInAsaas(any(), any(), any(), any()))
                .thenReturn(createPaymentResponse);

        when(signatureService.payWithCreditCard(any(), any()))
                .thenReturn(paymentResponse);

        when(signatureService.savePayment(paymentResponse))
                .thenReturn(payment);

        useCase.apply("user-123", value);

        assertEquals(2, signature.getRetryPayment());

        verify(recurrenceService, times(1))
                .sendCreatePaymentRecurrence(any());

        assertEquals(SignatureStatus.ATIVA, signature.getStatus());
    }

    @Test
    void shouldInactivateWhenRetryGreaterThanThree() {

        signature.setRetryPayment(4);

        var createPaymentResponse = new CreatePaymentResponse();
        createPaymentResponse.id = "payment-id";

        var paymentResponse = new PaymentResponse();
        paymentResponse.status = PaymentStatus.PENDING;

        when(userService.findUserByIdentifier("user-123"))
                .thenReturn(user);

        when(signatureService.createPaymentInAsaas(any(), any(), any(), any()))
                .thenReturn(createPaymentResponse);

        when(signatureService.payWithCreditCard(any(), any()))
                .thenReturn(paymentResponse);

        when(signatureService.savePayment(paymentResponse))
                .thenReturn(payment);

        useCase.apply("user-123", value);

        assertEquals(SignatureStatus.INATIVA, signature.getStatus());

        verify(recurrenceService, never())
                .sendCreatePaymentRecurrence(any());
    }

}