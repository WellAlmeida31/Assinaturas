package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.service.UserService;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExecuteSubscriptionSuspensionUseCase {

    private final UserService userService;


    @Transactional
    public void apply(String userIdentifier){
        log.info("Execute Subscription Suspension of Signature to User: {}", userIdentifier);

        var user = userService.findUserByIdentifier(userIdentifier);

        user.getSignatureDbo().setStatus(SignatureStatus.CANCELADA);
    }
}
