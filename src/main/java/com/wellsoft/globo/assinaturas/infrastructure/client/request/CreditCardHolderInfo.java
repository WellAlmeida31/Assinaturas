package com.wellsoft.globo.assinaturas.infrastructure.client.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCardHolderInfo {
    private String name;
    private String email;
    private String cpfCnpj;
    private String postalCode;
    private String addressNumber;
    private String phone;
}
