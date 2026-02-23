package com.wellsoft.globo.assinaturas.infrastructure.persistence.repository;

import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends UserProvider, JpaRepository<UserDbo, Long> {

    @Transactional
    @Override
    default UserDbo saveUser(UserDbo userDbo){
        return this.save(userDbo);
    }

    @Transactional(readOnly = true)
    @Override
    default Optional<UserDbo> findUserByCpf(String cpf){
        return findByCpf(cpf);
    }

    Optional<UserDbo> findByCpf(String cpf);

    @Override
    @Transactional(readOnly = true)
    default UserDbo findUserByIdentifier(String identifier) {
        return findByIdentifier(identifier)
                .orElseThrow(() -> new EntityNotFoundException("User not found with identifier: " + identifier));
    }

    @Query("SELECT u FROM UserDbo u " +
            "LEFT JOIN FETCH u.creditCardDbo " +
            "LEFT JOIN FETCH u.signatureDbo " +
            "WHERE u.userIdentifier = :identifier")
    Optional<UserDbo> findByIdentifier(@Param("identifier") String identifier);


}
