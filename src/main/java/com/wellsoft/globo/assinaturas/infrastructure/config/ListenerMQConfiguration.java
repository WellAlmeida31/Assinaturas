package com.wellsoft.globo.assinaturas.infrastructure.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class ListenerMQConfiguration extends AbstractRabbitMQConfiguration {

    public static final String CREATE_RECURRENCE = "createRecurrence";


    private final JsonMapper jsonMapper;
    private final MessageProperties assinaturasProperties;


    @Override
    protected JsonMapper getJsonMapper() {
        return  jsonMapper;
    }

    @ConfigurationProperties("com.wellsoft.amqp")
    @org.springframework.context.annotation.Configuration
    @Data
    public static class MessageProperties {
        private MessageConfiguration createRecurrence;

    }

    @Bean(CREATE_RECURRENCE)
    SimpleRabbitListenerContainerFactory containerFactoryOperatorCampaign(final ConnectionFactory connectionFactory,
                                                                  final ListenerMQConfiguration rabbitMQConfiguration) {
        return buildFactory(assinaturasProperties.getCreateRecurrence(), connectionFactory, rabbitMQConfiguration);
    }


    private SimpleRabbitListenerContainerFactory buildFactory(MessageConfiguration configurationProperties,
                                                              final ConnectionFactory connectionFactory,
                                                              final ListenerMQConfiguration rabbitMQConfiguration) {

        return rabbitMQConfiguration.listenerContainerFactory(Configuration.builder()
                .connectionFactory(connectionFactory)
                .concurrentConsumer(configurationProperties.getConcurrentConsumer())
                .maxConcurrentConsumer(configurationProperties.getMaxConcurrentConsumer())
                .maxAttempts(configurationProperties.getMaxAttempts())
                .prefetch(configurationProperties.getPrefetch())
                .build());
    }

}
