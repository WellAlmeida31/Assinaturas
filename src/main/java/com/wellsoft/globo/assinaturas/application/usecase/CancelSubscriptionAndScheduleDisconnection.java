package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.application.dto.CancelSignatureDto;
import com.wellsoft.globo.assinaturas.domain.exception.AlreadySubscription;
import com.wellsoft.globo.assinaturas.domain.exception.ExistentUserException;
import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.domain.service.RecurrenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelSubscriptionAndScheduleDisconnection {

    private final UserProvider userProvider;
    private final RecurrenceService recurrenceService;

    public void apply(String identifier){

        var user = userProvider.findUserByIdentifier(identifier);
        if(isNull(user.getSignatureDbo())){
            throw new EntityNotFoundException("The user not found");
        }

        recurrenceService.sendCancelPaymentRecurrenceAndSignature(CancelSignatureDto.builder()
                .userIdentifier(identifier)
                .expirationDate(user.getSignatureDbo().getExpirationDate())
                .build());
        log.info("The cancellation of the subscription and recurring payment has been scheduled");
    }
}
