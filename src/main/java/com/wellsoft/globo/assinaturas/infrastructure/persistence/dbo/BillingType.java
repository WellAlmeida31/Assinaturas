package com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo;

import lombok.Getter;

@Getter
public enum BillingType {
    UNDEFINED,
    CREDIT_CARD,
    DEBIT_CARD,
    PIX;
}
