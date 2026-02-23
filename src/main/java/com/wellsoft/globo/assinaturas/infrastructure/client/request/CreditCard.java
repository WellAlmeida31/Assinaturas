package com.wellsoft.globo.assinaturas.infrastructure.client.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCard {
    private String holderName;
    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String ccv;
}
