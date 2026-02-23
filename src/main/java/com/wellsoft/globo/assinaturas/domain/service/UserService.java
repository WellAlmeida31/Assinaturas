package com.wellsoft.globo.assinaturas.domain.service;

import com.wellsoft.globo.assinaturas.domain.exception.CreateClientException;
import com.wellsoft.globo.assinaturas.infrastructure.client.AsaasPaymentClient;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.AsaasClientResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.UserMapper;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AsaasPaymentClient paymentClient;
    private final UserMapper userMapper;

    public AsaasClientResponse createAsaasUser(UserRequestDto userRequestDto){
        log.info("Create User in Asaas Server");
        return Optional
                .ofNullable(paymentClient.createClient(userMapper.toClientRequest(userRequestDto)))
                .orElseThrow(()-> new CreateClientException("Error creating user"));
    }
}
