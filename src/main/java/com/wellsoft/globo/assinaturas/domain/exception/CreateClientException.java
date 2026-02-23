package com.wellsoft.globo.assinaturas.domain.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateClientException extends RuntimeException {

    public CreateClientException(String message) {
        super(message);
    }
}
