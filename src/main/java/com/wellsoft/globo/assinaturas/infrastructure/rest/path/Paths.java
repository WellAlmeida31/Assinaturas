package com.wellsoft.globo.assinaturas.infrastructure.rest.path;

public interface Paths {

    String VERSION_APP_V1 = "/v1";
    String VERSION_APP_V2 = "/v2";
    String APP = "/api" + VERSION_APP_V1 + "/client";

    interface USER {
        String CREATE = "/user/create";
        String IDENTIFIER = "/{identifier}";
        String FIND = "/find" + IDENTIFIER;
    }

    interface Placeholder {
        String IDENTIFIER = "identifier";
        String CPF = "cpf";
        String STATUS = "status";
    }

}
