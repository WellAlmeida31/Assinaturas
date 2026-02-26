package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.Plan;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseDto(
        Long id,
        String userIdentifier,
        String name,
        String cpf,
        String email,
        String phone,
        String postalCode,
        String addressNumber,
        String address,
        String province,
        String cityName,
        String state,
        String country,
        String clientCustomerId,
        LocalDateTime createdAt,
        CreditCardDto creditCard,
        SignatureDto signature) {

    public record CreditCardDto(
            String holderName,
            String number,
            String expiryMonth,
            String expiryYear,
            String ccv,
            String creditCardToken,
            String creditCardBrand){}

    public record SignatureDto(
            Plan plan,
            LocalDateTime startDate,
            LocalDateTime expirationDate,
            SignatureStatus status){}

}
