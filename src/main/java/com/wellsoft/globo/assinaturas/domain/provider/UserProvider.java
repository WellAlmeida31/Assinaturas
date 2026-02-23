package com.wellsoft.globo.assinaturas.domain.provider;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;

import java.util.Optional;

public interface UserProvider {

    UserDbo saveUser(UserDbo userDbo);
    UserDbo findUserByIdentifier(String identifier);
    Optional<UserDbo> findUserByCpf(String cpf);
}
