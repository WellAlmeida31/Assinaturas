package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureDbo;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureStatus;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExecuteSubscriptionSuspensionTest {

    @Mock
    private UserProvider userProvider;

    @InjectMocks
    private ExecuteSubscriptionSuspension useCase;

    private UserDbo user;
    private SignatureDbo signature;

    @BeforeEach
    void setup() {

        signature = new SignatureDbo();
        signature.setStatus(SignatureStatus.ATIVA);

        user = UserDbo.builder()
                .userIdentifier("user-123")
                .build();
        user.setSignatureDbo(signature);
    }

    @Test
    void shouldCancelSubscription() {

        when(userProvider.findUserByIdentifier("user-123"))
                .thenReturn(user);

        useCase.apply("user-123");

        assertEquals(SignatureStatus.CANCELADA, signature.getStatus());

        verify(userProvider, times(1))
                .findUserByIdentifier("user-123");
    }


}