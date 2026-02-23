package com.wellsoft.globo.assinaturas.infrastructure.client.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class AsaasClientRequest {
    private String name;
    private String cpfCnpj;
    private String email;
    private String mobilePhone;
    private String addressNumber;
    private String postalCode;
    private String externalReference;
}
