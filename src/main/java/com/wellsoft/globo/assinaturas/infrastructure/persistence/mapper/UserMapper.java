package com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper;

import com.wellsoft.globo.assinaturas.infrastructure.client.request.AsaasClientRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.AsaasClientResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.UserDbo;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserCreateResponseDto;
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

    UserCreateResponseDto toUserCreateDto(UserDbo userDbo);

    @Mapping(target = "creditCard.holderName", source = "userDbo.creditCardDbo.holderName")
    @Mapping(target = "creditCard.number", source = "userDbo.creditCardDbo.number")
    @Mapping(target = "creditCard.expiryMonth", source = "userDbo.creditCardDbo.expiryMonth")
    @Mapping(target = "creditCard.expiryYear", source = "userDbo.creditCardDbo.expiryYear")
    @Mapping(target = "creditCard.ccv", source = "userDbo.creditCardDbo.ccv")
    @Mapping(target = "creditCard.creditCardToken", source = "userDbo.creditCardDbo.creditCardToken")
    @Mapping(target = "creditCard.creditCardBrand", source = "userDbo.creditCardDbo.creditCardBrand")
    @Mapping(target = "signature.plan", source = "userDbo.signatureDbo.plan")
    @Mapping(target = "signature.startDate", source = "userDbo.signatureDbo.startDate")
    @Mapping(target = "signature.expirationDate", source = "userDbo.signatureDbo.expirationDate")
    @Mapping(target = "signature.status", source = "userDbo.signatureDbo.status")
    UserResponseDto toUserDto(UserDbo userDbo);

    default String generateExternalReference() {
        return UUID.randomUUID().toString();
    }
}
