package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.domain.service.UserService;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.UserMapper;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserCreateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserService userService;
    private final UserProvider userProvider;
    private final UserMapper userMapper;

    public UserCreateResponseDto createUser(UserRequestDto userRequestDto){
        var asaasUser = userService.createAsaasUser(userRequestDto);
        var user = userProvider.saveUser(userMapper.toUserDbo(asaasUser));
        return userMapper.toUserCreateDto(user);
    }
}
