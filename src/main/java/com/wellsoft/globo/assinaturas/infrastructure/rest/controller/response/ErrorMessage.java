package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.FieldError;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorMessage(
        String message,
        String localizedItem) {

    public ErrorMessage(FieldError fieldError) {
        this(fieldError.getDefaultMessage(), fieldError.getField());
    }
}
