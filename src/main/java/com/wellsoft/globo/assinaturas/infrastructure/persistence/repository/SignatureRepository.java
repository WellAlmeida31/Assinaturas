package com.wellsoft.globo.assinaturas.infrastructure.persistence.repository;

import com.wellsoft.globo.assinaturas.domain.provider.SignatureProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SignatureRepository extends SignatureProvider, JpaRepository<SignatureDbo, Long> {

    @Transactional
    @Override
    default SignatureDbo createSignature(SignatureDbo signature){
        return this.save(signature);
    }
}
