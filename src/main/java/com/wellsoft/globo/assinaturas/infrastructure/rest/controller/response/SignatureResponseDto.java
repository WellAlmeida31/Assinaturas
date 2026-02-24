package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.Plan;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SignatureResponseDto(
        String identifier,
        Plan plan,
        LocalDateTime startDate,
        LocalDateTime expirationDate,
        String holderName,
        String number,
        String expiryMonth,
        String expiryYear,
        String ccv,
        String taxId,
        String email,
        String postalCode,
        String addressHolderNumber,
        String phone,
        String creditCardBrand,
        String transactionReceiptUrl) {
}
