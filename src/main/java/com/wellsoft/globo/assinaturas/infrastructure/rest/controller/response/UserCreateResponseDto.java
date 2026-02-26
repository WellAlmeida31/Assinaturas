package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserCreateResponseDto(
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
        String clientCustomerId) {
}
