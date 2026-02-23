package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequestDto(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be at most 255 characters")
        String name,

        @NotBlank(message = "CPF is required")
        @CPF(message = "Invalid CPF")
        String cpf,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255, message = "Email must be at most 255 characters")
        String email,

        @NotBlank(message = "Phone is required")
        @Size(max = 20, message = "Phone must be at most 20 characters")
        String phone,

        @NotBlank(message = "Postal code is required")
        @Size(max = 10, message = "Postal code must be at most 10 characters")
        String postalCode,

        @Size(max = 10, message = "Address number must be at most 10 characters")
        String addressNumber) {
}
