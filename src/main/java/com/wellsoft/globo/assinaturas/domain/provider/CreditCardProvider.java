package com.wellsoft.globo.assinaturas.domain.provider;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.CreditCardDbo;

public interface CreditCardProvider {

    CreditCardDbo saveCreditCardWithToken(CreditCardDbo creditCard);
}
