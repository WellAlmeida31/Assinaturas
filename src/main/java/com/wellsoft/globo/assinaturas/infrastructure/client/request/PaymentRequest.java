package com.wellsoft.globo.assinaturas.infrastructure.client.request;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.BillingType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentRequest {
    private BillingType billingType;
    private String customer;
    private BigDecimal value;
    private String dueDate;
}
