package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.service.UserService;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.UserMapper;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FindUserAndSignaturesAndCreditCardUseCase {

    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "usersByIdentifier", key = "#identifier")
    public UserResponseDto findByIdentifier(String identifier){
        var userDbo = userService.findUserByIdentifier(identifier);
        return userMapper.toUserDto(userDbo);
    }

}
