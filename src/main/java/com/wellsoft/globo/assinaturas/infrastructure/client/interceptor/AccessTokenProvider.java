package com.wellsoft.globo.assinaturas.infrastructure.client.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import java.util.Map;

@Slf4j
@Component
public class AccessTokenProvider {

    private final SecretsManagerClient secretsManagerClient;
    private final String secretName;
    @Getter
    private String token;

    public AccessTokenProvider(SecretsManagerClient secretsManagerClient,
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

        this.token = (String) new ObjectMapper().readValue(secretValue.secretString(), Map.class).get("token");
        log.info("Access Token Asaas loaded successfully");
    }

}
