package com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.CreditCardDbo;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.SignatureRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {

    CreditCardDbo toCreditCardDbo(SignatureRequestDto dto);

}
