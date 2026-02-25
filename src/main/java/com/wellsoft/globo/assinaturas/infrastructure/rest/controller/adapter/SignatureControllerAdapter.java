package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.adapter;

import com.wellsoft.globo.assinaturas.application.usecase.CancelSubscriptionAndScheduleDisconnection;
import com.wellsoft.globo.assinaturas.application.usecase.CreateSignatureAndPaymentUseCase;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.port.SignatureController;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.SignatureRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.SignatureResponseDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.path.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignatureControllerAdapter implements SignatureController {

    private final CreateSignatureAndPaymentUseCase createSignatureAndPaymentUseCase;
    private final CancelSubscriptionAndScheduleDisconnection cancelSubscriptionAndScheduleDisconnection ;

    @Override
    public ResponseEntity<SignatureResponseDto> createSignatureToUser(SignatureRequestDto dto, UriComponentsBuilder uriBuilder) {
        log.info("Start create signature to user, identifier: {}", dto.identifier());
        var signatureDto = createSignatureAndPaymentUseCase.createAndPayment(dto);
        return ResponseEntity
                .created(uriBuilder
                        .path(Paths.APP + Paths.SIGNATURE.FIND)
                        .buildAndExpand(signatureDto.identifier())
                        .toUri())
                .body(signatureDto);
    }

    @Override
    public ResponseEntity<Void> cancelSignatureToUser(String identifier) {
        log.info("Start delete signature to user, identifier: {}", identifier);
        cancelSubscriptionAndScheduleDisconnection.apply(identifier);
        return ResponseEntity.noContent().build();
    }
}
