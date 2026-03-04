package com.wellsoft.globo.assinaturas.domain.service;

import com.wellsoft.globo.assinaturas.domain.exception.CreateClientException;
import com.wellsoft.globo.assinaturas.domain.provider.UserProvider;
import com.wellsoft.globo.assinaturas.infrastructure.client.AsaasPaymentClient;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.AsaasClientResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.UserMapper;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AsaasPaymentClient paymentClient;
    private final UserProvider userProvider;
    private final UserMapper userMapper;

    public AsaasClientResponse createAsaasUser(UserRequestDto userRequestDto){
        log.info("Create User in Asaas Server");
        return Optional
                .ofNullable(paymentClient.createClient(userMapper.toClientRequest(userRequestDto)))
                .orElseThrow(()-> new CreateClientException("Error creating user"));
    }

    public UserDbo findUserByIdentifier(String identifier){
        log.info("Find User by identifier: {}", identifier);
        return userProvider.findUserByIdentifier(identifier);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "usersByCpf", key = "#cpf")
    public Optional<UserResponseDto> findUserByCpf(String cpf){
        log.info("Find User by cpf: {}", cpf);
        return userProvider.findUserByCpf(cpf)
                .map(userMapper::toUserDto);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDbo saveUser(UserDbo userDbo){
        log.info("Saving User: {}", userDbo);
        return userProvider.saveUser(userDbo);
    }
}
