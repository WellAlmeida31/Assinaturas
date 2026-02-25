package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExecuteSubscriptionSuspension {

    private final UserProvider userProvider;

    @Transactional
    public void apply(String userIdentifier){
        log.info("Execute Subscription Suspension of Signature to User: {}", userIdentifier);

        var user = userProvider.findUserByIdentifier(userIdentifier);

        user.getSignatureDbo().setStatus(SignatureStatus.CANCELADA);
    }
}
