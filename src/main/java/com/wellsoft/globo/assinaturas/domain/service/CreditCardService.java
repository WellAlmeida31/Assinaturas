package com.wellsoft.globo.assinaturas.domain.service;

import com.wellsoft.globo.assinaturas.domain.provider.CreditCardProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.CreditCardDbo;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper.CreditCardMapper;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.SignatureRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditCardService {

    private final CreditCardProvider creditCardProvider;
    private final CreditCardMapper creditCardMapper ;

    public CreditCardDbo saveCreditCardWithToken(SignatureRequestDto dto){
        log.info("Saving Credit Card with token");
        return creditCardProvider.saveCreditCardWithToken(creditCardMapper.toCreditCardDbo(dto));
    }


}
