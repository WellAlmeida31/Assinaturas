package com.wellsoft.globo.assinaturas.infrastructure.config;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.ImmediateRequeueAmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.util.Assert;
import tools.jackson.databind.json.JsonMapper;

import java.util.Arrays;

@Slf4j
public abstract class AbstractRabbitMQConfiguration implements RabbitListenerConfigurer {

    @Data
    public static class MessageConfiguration {
        private String queueName;
        private String exchangeName;
        private int concurrentConsumer;
        private int maxConcurrentConsumer;
        private int maxAttempts;
        private int prefetch;
    }

    @Builder
    @Getter
    @ToString
    protected static final class Configuration {

        private static final class CustomConfigurationBuilder extends ConfigurationBuilder {

            private void valid(final Configuration configuration) {
                Assert.notNull(configuration.getConnectionFactory(),
                        "Connection Factory can not be null");
                Assert.isTrue(configuration.getConcurrentConsumer() >= 1,
                        "Concurrent Consumer must not be negative");
                Assert.isTrue(configuration.getMaxConcurrentConsumer() >= 1,
                        "Max Concurrent Consumer must not be negative");
                Assert.isTrue(configuration.getMaxConcurrentConsumer() >= configuration.getConcurrentConsumer(),
                        "Max Concurrent Consumer must be greater or equals than Concurrent Consumer");
                Assert.isTrue(configuration.getMaxAttempts() >= 1,
                        "Max attempts must not be negative");
                Assert.isTrue(configuration.getInitialInterval() >= 1L,
                        "Initial Interval must not be negative");
                Assert.isTrue(configuration.getMaxInterval() >= 1L,
                        "Max Interval must not be negative");
                Assert.isTrue(configuration.getMultiplier() >= 0.1,
                        "Multiple must not be negative");
            }

            @Override
            public Configuration build() {
                final Configuration configuration = super.build();
                valid(configuration);
                return configuration;
            }
        }

        private ConnectionFactory connectionFactory;
        @Default
        private int concurrentConsumer = 1;
        @Default
        private int maxConcurrentConsumer = 1;
        @Default
        private int prefetch = 100;
        @Default
        private int maxAttempts = 3;
        @Default
        private long initialInterval = 1000L;
        @Default
        private double multiplier = 1.0;
        @Default
        private long maxInterval = 10_000L;

        public static ConfigurationBuilder builder() {
            return new CustomConfigurationBuilder();
        }
    }

    protected abstract JsonMapper getJsonMapper();

    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter(getJsonMapper());
    }

    @Bean
    public DefaultClassMapper classMapper() {
        return new DefaultClassMapper();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new org.springframework.amqp.support.converter.JacksonJsonMessageConverter(getJsonMapper()));
        return rabbitTemplate;
    }

    @Bean
    public JacksonJsonMessageConverter consumerJackson2MessageConverter() {
        return new JacksonJsonMessageConverter(getJsonMapper());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        final var factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    protected SimpleRabbitListenerContainerFactory listenerContainerFactory(final Configuration configuration) {
        final var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(configuration.getConnectionFactory());
        factory.setConcurrentConsumers(configuration.getConcurrentConsumer());
        factory.setMaxConcurrentConsumers(configuration.getMaxConcurrentConsumer());
        factory.setDefaultRequeueRejected(false);
        factory.setPrefetchCount(configuration.getPrefetch());
        factory.setAdviceChain(retryConfig(configuration));
        return factory;
    }

    private RetryOperationsInterceptor retryConfig(final Configuration configuration) {
        final RetryTemplate retryTemplate = getRetryTemplate(configuration);
        retryTemplate.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(final RetryContext context, final RetryCallback<T, E> callback) {
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(final RetryContext context,
                                                       final RetryCallback<T, E> callback,
                                                       final Throwable throwable) {
                if (throwable != null) {
                    log.error("Failed: Retry count {}", context.getRetryCount(), throwable);
                }
            }

            @Override
            public <T, E extends Throwable> void onError(final RetryContext context,
                                                         final RetryCallback<T, E> callback,
                                                         final Throwable throwable) {
                log.error("Retry count {}", context.getRetryCount(), throwable);
            }
        });

        final var interceptor = new RetryOperationsInterceptor();
        interceptor.setRetryOperations(retryTemplate);
        interceptor.setRecoverer((objects, throwable) -> {
            if (throwable != null) {
                throw new ImmediateRequeueAmqpException(throwable);
            } else {
                throw new ImmediateRequeueAmqpException("Re-queueing for message: " + Arrays.toString(objects));
            }
        });

        return interceptor;
    }

    private static RetryTemplate getRetryTemplate(Configuration configuration) {
        final RetryPolicy retryPolicy = new SimpleRetryPolicy(configuration.getMaxAttempts());

        final var backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(configuration.getInitialInterval());
        backOffPolicy.setMultiplier(configuration.getMultiplier());
        backOffPolicy.setMaxInterval(configuration.getMaxInterval());

        final RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

}
