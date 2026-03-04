package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.exception.AlreadySubscription;
import com.wellsoft.globo.assinaturas.domain.exception.PaymentFailedException;
import com.wellsoft.globo.assinaturas.domain.provider.PlanValueProvider;
import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.domain.service.CreditCardService;
import com.wellsoft.globo.assinaturas.domain.service.RecurrenceService;
import com.wellsoft.globo.assinaturas.domain.service.SignatureService;
import com.wellsoft.globo.assinaturas.domain.service.UserService;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.CreatePaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.PaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.TokenizeCreditCardResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.*;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.SignatureRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSignatureAndPaymentUseCaseTest {

    @Mock
    private SignatureService signatureService;

    @Mock
    private CreditCardService creditCardService;

    @Mock
    private RecurrenceService recurrenceService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CreateSignatureAndPaymentUseCase useCase;

    private SignatureRequestDto request;
    private UserDbo user;

    @BeforeEach
    void setup() {

        request = new SignatureRequestDto(
                "user-123",
                Plan.BASICO,
                "4111111111111111",
                "12",
                "2030",
                "123",
                "John Doe",
                "100",
                "john@email.com",
                "99999999999",
                "12345678",
                "11999999999",
                "127.0.0.1"
        );

        user = UserDbo.builder()
                .userIdentifier("user-123")
                .clientCustomerId("cust-123")
                .build();
    }


    @Test
    void shouldCreateSubscriptionAndScheduleRecurrence() {

        var signature = new SignatureDbo();
        var planValue = new BigDecimal("59.90");

        var createPaymentResponse = new CreatePaymentResponse();
        createPaymentResponse.id = "payment-id";

        var tokenResponse = new TokenizeCreditCardResponse();
        tokenResponse.setCreditCardBrand("VISA");
        tokenResponse.setCreditCardToken("token-123");

        var paymentResponse = new PaymentResponse();
        paymentResponse.status = PaymentStatus.CONFIRMED;
        paymentResponse.transactionReceiptUrl = "url-receipt";

        var paymentDbo = new PaymentsDbo();
        var creditCardDbo = new CreditCardDbo();

        when(userService.findUserByIdentifier("user-123")).thenReturn(user);
        when(signatureService.createInitialSignature(request)).thenReturn(signature);
        when(signatureService.getPlanValue(request.plan())).thenReturn(planValue);
        when(signatureService.createPaymentInAsaas(any(), any(), any(), any()))
                .thenReturn(createPaymentResponse);
        when(signatureService.tokenizedCreditCard(any()))
                .thenReturn(tokenResponse);
        when(signatureService.payWithCreditCard(any(), any()))
                .thenReturn(paymentResponse);
        when(signatureService.savePayment(paymentResponse))
                .thenReturn(paymentDbo);
        when(creditCardService.saveCreditCardWithToken(request))
                .thenReturn(creditCardDbo);

        var response = useCase.createAndPayment(request);

        assertNotNull(response);
        assertEquals("user-123", response.identifier());
        assertEquals("url-receipt", response.transactionReceiptUrl());

        verify(recurrenceService, times(1))
                .sendCreatePaymentRecurrence(any());

        assertEquals(SignatureStatus.ATIVA, signature.getStatus());
        assertNotNull(signature.getStartDate());
        assertNotNull(signature.getExpirationDate());
    }

    @Test
    void shouldThrowWhenUserAlreadyHasSubscription() {

        user.setSignatureDbo(new SignatureDbo());

        when(userService.findUserByIdentifier("user-123"))
                .thenReturn(user);

        assertThrows(AlreadySubscription.class,
                () -> useCase.createAndPayment(request));

        verifyNoInteractions(signatureService);
    }

    @Test
    void shouldThrowWhenPaymentNotConfirmed() {

        var signature = new SignatureDbo();
        var createPaymentResponse = new CreatePaymentResponse();
        createPaymentResponse.id = "payment-id";

        var tokenResponse = new TokenizeCreditCardResponse();
        tokenResponse.setCreditCardToken("token-123");

        var paymentResponse = new PaymentResponse();
        paymentResponse.status = PaymentStatus.PENDING;

        when(userService.findUserByIdentifier("user-123")).thenReturn(user);
        when(signatureService.createInitialSignature(request)).thenReturn(signature);
        when(signatureService.getPlanValue(request.plan())).thenReturn(new BigDecimal("59.90"));
        when(signatureService.createPaymentInAsaas(any(), any(), any(), any()))
                .thenReturn(createPaymentResponse);
        when(signatureService.tokenizedCreditCard(any()))
                .thenReturn(tokenResponse);
        when(signatureService.payWithCreditCard(any(), any()))
                .thenReturn(paymentResponse);

        assertThrows(PaymentFailedException.class,
                () -> useCase.createAndPayment(request));

        verify(signatureService, never()).savePayment(any());
        verify(recurrenceService, never()).sendCreatePaymentRecurrence(any());
    }

}