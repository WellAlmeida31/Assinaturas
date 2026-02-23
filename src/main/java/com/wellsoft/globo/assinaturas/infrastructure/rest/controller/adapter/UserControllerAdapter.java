package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.adapter;

import com.wellsoft.globo.assinaturas.application.usecase.CreateUserUseCase;
import com.wellsoft.globo.assinaturas.application.usecase.FindUserAndSignaturesAndCreditCard;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.port.UserController;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserCreateResponseDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserResponseDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.path.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserControllerAdapter implements UserController {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserAndSignaturesAndCreditCard findUser;

    @Override
    public ResponseEntity<UserCreateResponseDto> createUser(UserRequestDto dto, UriComponentsBuilder uriBuilder) {
        log.info("Start create user");
        var userDto = createUserUseCase.createUser(dto);
        return ResponseEntity
                .created(uriBuilder
                        .path(Paths.APP + Paths.USER.FIND)
                        .buildAndExpand(userDto.userIdentifier())
                        .toUri())
                .body(userDto);
    }

    @Override
    public ResponseEntity<UserResponseDto> findUserByIdentifier(String identifier) {
        log.info("Start find user by Identifier");
        return ResponseEntity.ok(findUser.findByIdentifier(identifier));
    }
}
