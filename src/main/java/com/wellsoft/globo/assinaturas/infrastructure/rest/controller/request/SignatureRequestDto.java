package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.Plan;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.CreditCardNumber;

public record SignatureRequestDto(
    @NotBlank(message = "Identifier is required")
    @Size(max = 100, message = "Identifier must be at most 100 characters")
    String identifier,

    @NotNull(message = "Plan is required")
    Plan plan,

    @NotBlank(message = "Holder name is required")
    @Size(max = 255, message = "Holder name must be at most 255 characters")
    String holderName,

    @NotBlank(message = "Card number is required")
    @CreditCardNumber(message = "Invalid credit card number")
    String number,

    @NotBlank(message = "Expiry month is required")
    @Size(min = 2, max = 2, message = "Expiry month must be 2 digits")
    String expiryMonth,

    @NotBlank(message = "Expiry year is required")
    @Size(min = 4, max = 4, message = "Expiry year must be 4 digits")
    String expiryYear,

    @NotBlank(message = "CCV is required")
    @Size(min = 3, max = 4, message = "CCV must be 3 or 4 digits")
    String ccv,

    @NotBlank(message = "Tax ID is required")
    @Size(max = 20, message = "Tax ID must be at most 20 characters")
    String taxId,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must be at most 255 characters")
    String email,

    @NotBlank(message = "Postal code is required")
    @Size(max = 10, message = "Postal code must be at most 10 characters")
    String postalCode,

    @NotBlank(message = "Address holder number is required")
    @Size(max = 10, message = "Address holder number must be at most 10 characters")
    String addressHolderNumber,

    @NotBlank(message = "Phone is required")
    @Size(max = 20, message = "Phone must be at most 20 characters")
    String phone,

    @NotBlank(message = "IP is required")
    String remoteIp){
}
