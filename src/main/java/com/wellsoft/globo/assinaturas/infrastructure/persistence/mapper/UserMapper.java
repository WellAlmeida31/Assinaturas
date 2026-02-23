package com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper;

import com.wellsoft.globo.assinaturas.infrastructure.client.request.AsaasClientRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.AsaasClientResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "cpfCnpj", source = "userRequestDto.cpf")
    @Mapping(target = "mobilePhone", source = "userRequestDto.phone")
    @Mapping(target = "externalReference", expression = "java(generateExternalReference())")
    AsaasClientRequest toClientRequest(UserRequestDto userRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userIdentifier", source = "clientResponse.externalReference")
    @Mapping(target = "clientCustomerId", source = "clientResponse.id")
    @Mapping(target = "phone", source = "clientResponse.mobilePhone")
    @Mapping(target = "cpf", source = "clientResponse.cpfCnpj")
    UserDbo toUserDbo(AsaasClientResponse clientResponse);

    UserResponseDto toUserDto(UserDbo userDbo);

    default String generateExternalReference() {
        return UUID.randomUUID().toString();
    }
}
