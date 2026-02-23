package com.wellsoft.globo.assinaturas.infrastructure.client.response;

import lombok.Data;

@Data
public class CreditCardResponse {
    private String creditCardNumber;
    private String creditCardBrand;
    private String creditCardToken;
}
