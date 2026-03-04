package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.application.dto.CancelSignatureDto;
import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.domain.service.RecurrenceService;
import com.wellsoft.globo.assinaturas.domain.service.UserService;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureDbo;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelSubscriptionAndScheduleDisconnectionUseCaseTest {

    @Mock
    private UserService userService;

    @Mock
    private RecurrenceService recurrenceService;

    @InjectMocks
    private CancelSubscriptionAndScheduleDisconnectionUseCase useCase;

    private final String identifier = "user-123";

    private UserDbo user;
    private SignatureDbo signature;

    @BeforeEach
    void setup() {
        signature = new SignatureDbo();
        signature.setExpirationDate(LocalDateTime.now().plusDays(10));

        user = new UserDbo();
        user.setSignatureDbo(signature);
    }

    @Test
    void shouldCancelSubscriptionAndScheduleDisconnectionSuccessfully() {
        when(userService.findUserByIdentifier(identifier)).thenReturn(user);

        useCase.apply(identifier);

        ArgumentCaptor<CancelSignatureDto> captor =
                ArgumentCaptor.forClass(CancelSignatureDto.class);

        verify(recurrenceService, times(1))
                .sendCancelPaymentRecurrenceAndSignature(captor.capture());

        CancelSignatureDto capturedDto = captor.getValue();

        assertEquals(identifier, capturedDto.getUserIdentifier());
        assertEquals(signature.getExpirationDate(), capturedDto.getExpirationDate());
    }

    @Test
    void shouldThrowExceptionWhenUserHasNoSignature() {
        user.setSignatureDbo(null);
        when(userService.findUserByIdentifier(identifier)).thenReturn(user);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> useCase.apply(identifier)
        );

        assertEquals("The user not found", exception.getMessage());

        verify(recurrenceService, never())
                .sendCancelPaymentRecurrenceAndSignature(any());
    }

}