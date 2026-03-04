package com.wellsoft.globo.assinaturas.application.usecase;

import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.domain.service.UserService;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.UserMapper;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserAndSignaturesAndCreditCardUseCaseTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private FindUserAndSignaturesAndCreditCardUseCase useCase;

    @Test
    void shouldReturnUserResponseDto() {

        var identifier = "user-123";

        var userDbo = UserDbo.builder().userIdentifier(identifier).build();

        var responseDto = UserResponseDto.builder()
                .userIdentifier(identifier)
                .build();

        when(userService.findUserByIdentifier(identifier))
                .thenReturn(userDbo);

        when(userMapper.toUserDto(userDbo))
                .thenReturn(responseDto);

        var result = useCase.findByIdentifier(identifier);

        assertNotNull(result);
        assertEquals(identifier, result.userIdentifier());

        verify(userService, times(1))
                .findUserByIdentifier(identifier);

        verify(userMapper, times(1))
                .toUserDto(userDbo);
    }

    @Test
    void shouldPropagateExceptionWhenUserNotFound() {

        var identifier = "user-123";

        when(userService.findUserByIdentifier(identifier))
                .thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class,
                () -> useCase.findByIdentifier(identifier));

        verify(userMapper, never())
                .toUserDto(any());
    }

}