package com.wellsoft.globo.assinaturas.infrastructure.secrets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellsoft.globo.assinaturas.domain.provider.PlanValueProvider;
import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.Plan;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Component
public class PlanValueProviderImpl implements PlanValueProvider {

    private final SecretsManagerClient secretsManagerClient;
    private final String secretName;
    private BigDecimal basico;
    private BigDecimal premium;
    private BigDecimal familia;

    @Override
    public BigDecimal getPlanValue(Plan plan) {
        log.info("Get Plan Value for: {}", plan.name());
        return switch (plan){
            case BASICO -> this.basico;
            case PREMIUM -> this.premium;
            case FAMILIA -> this.familia;
        };
    }

    public PlanValueProviderImpl(SecretsManagerClient secretsManagerClient,
                               @Value("${access.secret}") String secretName) {
        this.secretsManagerClient = secretsManagerClient;
        this.secretName = secretName;
    }

    @SneakyThrows
    @PostConstruct
    public void init() {
        var secretValue = secretsManagerClient.getSecretValue(
                GetSecretValueRequest.builder()
                        .secretId(secretName)
                        .versionStage("AWSCURRENT")
                        .build()
        );

        this.basico = new BigDecimal((String) new ObjectMapper().readValue(secretValue.secretString(), Map.class).get("basico"));
        this.premium = new BigDecimal((String) new ObjectMapper().readValue(secretValue.secretString(), Map.class).get("premium"));
        this.familia = new BigDecimal((String) new ObjectMapper().readValue(secretValue.secretString(), Map.class).get("familia"));
        log.info("Plan values successfully loaded");
    }

}
