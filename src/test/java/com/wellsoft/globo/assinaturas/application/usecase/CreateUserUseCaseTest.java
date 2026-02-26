package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.exception.ExistentUserException;
import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.domain.service.UserService;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.AsaasClientResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.UserMapper;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserCreateResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserService userService;

    @Mock
    private UserProvider userProvider;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CreateUserUseCase useCase;

    private UserRequestDto request;

    @BeforeEach
    void setup() {
        request = new UserRequestDto(
                "Wellington",
                "99999999999",
                "email@email.com",
                "11999999999",
                "60078005",
                "10"
        );
    }

    @Test
    void shouldCreateUserSuccessfully() {

        var asaasUser = new AsaasClientResponse();
        var userDbo = UserDbo.builder().userIdentifier("user-123").build();

        var responseDto = UserCreateResponseDto.builder()
                .userIdentifier("user-123")
                .build();

        when(userProvider.findUserByCpf(request.cpf()))
                .thenReturn(Optional.empty());

        when(userService.createAsaasUser(request))
                .thenReturn(asaasUser);

        when(userMapper.toUserDbo(asaasUser))
                .thenReturn(userDbo);

        when(userProvider.saveUser(userDbo))
                .thenReturn(userDbo);

        when(userMapper.toUserCreateDto(userDbo))
                .thenReturn(responseDto);

        var response = useCase.createUser(request);

        assertNotNull(response);
        assertEquals("user-123", response.userIdentifier());

        verify(userService, times(1)).createAsaasUser(request);
        verify(userProvider, times(1)).saveUser(userDbo);
    }

    @Test
    void shouldThrowWhenUserAlreadyExists() {

        when(userProvider.findUserByCpf(request.cpf()))
                .thenReturn(Optional.of(new UserDbo()));

        assertThrows(ExistentUserException.class,
                () -> useCase.createUser(request));

        verify(userService, never()).createAsaasUser(any());
        verify(userProvider, never()).saveUser(any());
        verify(userMapper, never()).toUserDbo(any());
    }
}