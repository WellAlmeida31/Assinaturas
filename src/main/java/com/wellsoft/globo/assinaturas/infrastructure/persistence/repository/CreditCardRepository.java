package com.wellsoft.globo.assinaturas.infrastructure.persistence.repository;

import com.wellsoft.globo.assinaturas.domain.provider.CreditCardProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.CreditCardDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CreditCardRepository extends CreditCardProvider, JpaRepository<CreditCardDbo, Long> {

    @Transactional
    @Override
    default CreditCardDbo saveCreditCardWithToken(CreditCardDbo creditCard){
        return this.save(creditCard);
    }
}
