package com.wellsoft.globo.assinaturas.infrastructure.client.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayCreditCardRequest {
    private String creditCardToken;
}
