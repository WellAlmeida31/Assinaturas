package com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo;

import lombok.Getter;

@Getter
public enum SignatureStatus {
    ATIVA,
    INATIVA,
    CANCELADA;
}
