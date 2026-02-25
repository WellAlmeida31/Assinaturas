package com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureDbo;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureStatus;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.SignatureRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SignatureMapper {

    @Mapping(target = "userIdentifier", source = "dto.identifier")
    @Mapping(target = "plan", source = "dto.plan")
    @Mapping(target = "retryPayment", constant = "0")
    @Mapping(target = "status", expression = "java(generateInitialStatus())")
    SignatureDbo toSignatureDbo(SignatureRequestDto dto);

    default SignatureStatus generateInitialStatus() {
        return SignatureStatus.INATIVA;
    }
}
