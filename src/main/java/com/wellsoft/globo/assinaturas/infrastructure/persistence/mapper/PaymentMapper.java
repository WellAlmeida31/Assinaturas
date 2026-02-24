package com.wellsoft.globo.assinaturas.infrastructure.persistence.mapper;

import com.wellsoft.globo.assinaturas.infrastructure.client.response.PaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.PaymentsDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "clientCustomerId", source = "paymentResponse.customer")
    @Mapping(target = "dueDate", source = "paymentResponse.dueDate")
    @Mapping(target = "paymentId", source = "paymentResponse.id")
    @Mapping(target = "id", ignore = true)
    PaymentsDbo toPaymentDbo(PaymentResponse paymentResponse);



}
