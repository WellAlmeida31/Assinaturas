package com.wellsoft.globo.assinaturas.application.dto;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.BillingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecurrenceDto {
    private String userIdentifier;
    private BigDecimal value;
    BillingType billingType;
    private LocalDateTime recurrenceDate;
}
