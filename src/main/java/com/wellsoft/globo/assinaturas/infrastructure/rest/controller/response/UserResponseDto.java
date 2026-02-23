package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

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
        String clientCustomerId) {
}
