package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.UserMapper;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserAndSignaturesAndCreditCardUseCase {

    private final UserProvider userProvider;
    private final UserMapper userMapper;

    public UserResponseDto findByIdentifier(String identifier){
        var userDbo = userProvider.findUserByIdentifier(identifier);
        return userMapper.toUserDto(userDbo);
    }

}
