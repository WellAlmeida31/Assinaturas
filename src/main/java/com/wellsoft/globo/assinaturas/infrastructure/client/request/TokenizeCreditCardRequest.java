package com.wellsoft.globo.assinaturas.infrastructure.client.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenizeCreditCardRequest {
    private CreditCard creditCard;
    private CreditCardHolderInfo creditCardHolderInfo;
    private String customer;
    private String remoteIp;
}
