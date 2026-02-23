package com.wellsoft.globo.assinaturas.infrastructure.persistence.repository;

import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends UserProvider, JpaRepository<UserDbo, Long> {

    @Transactional
    @Override
    default UserDbo saveUser(UserDbo userDbo){
        return this.save(userDbo);
    }
}
