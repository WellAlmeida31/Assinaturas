package com.wellsoft.globo.assinaturas.domain.provider;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.Plan;

import java.math.BigDecimal;

public interface PlanValueProvider {

    BigDecimal getPlanValue(Plan plan);
}
