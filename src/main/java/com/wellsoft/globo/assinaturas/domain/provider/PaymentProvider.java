package com.wellsoft.globo.assinaturas.domain.provider;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.PaymentsDbo;

public interface PaymentProvider {

    PaymentsDbo createPayment(PaymentsDbo paymentsDbo);
}
