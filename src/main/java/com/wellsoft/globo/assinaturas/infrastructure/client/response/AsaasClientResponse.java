package com.wellsoft.globo.assinaturas.infrastructure.client.response;

import lombok.Data;

@Data
public class AsaasClientResponse {
    private String object;
    private String id;
    private String dateCreated;
    private String name;
    private String email;
    private String mobilePhone;
    private String address;
    private String addressNumber;
    private String province;
    private String postalCode;
    private String cpfCnpj;
    private String personType;
    private String externalReference;
    private boolean deleted;
    private boolean notificationDisabled;
    private boolean canDelete;
    private boolean canEdit;
    private int city;
    private String cityName;
    private String state;
    private String country;
}
