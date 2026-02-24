package com.wellsoft.globo.assinaturas.infrastructure.persistence.repository;

import com.wellsoft.globo.assinaturas.domain.provider.PaymentProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.PaymentsDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentRepository extends PaymentProvider, JpaRepository<PaymentsDbo, Long> {

    @Transactional
    @Override
    default PaymentsDbo createPayment(PaymentsDbo payments){
        return this.save(payments);
    }

}
