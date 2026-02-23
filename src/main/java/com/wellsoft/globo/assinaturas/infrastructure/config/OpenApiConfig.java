package com.wellsoft.globo.assinaturas.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("#{systemProperties['openapi.dev-url'] ?: 'http://localhost:8080'}")
    private String devUrl;

    @Bean
    public OpenAPI openAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor no ambiente de Desenvolvimento");

        Contact contact = new Contact();
        contact.setEmail("welldevjava@outlook.com");
        contact.setName("Wellington Almeida");

        License mit = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Assinaturas Globo - API")
                .version("1.0")
                .contact(contact)
                .description("Teste Técnico - Globo / NT Consult").termsOfService("")
                .license(mit);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
