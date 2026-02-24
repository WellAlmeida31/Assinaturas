package com.wellsoft.globo.assinaturas.infrastructure.messaging.rabbitmq.producer;

import com.wellsoft.globo.assinaturas.application.dto.CreateRecurrenceDto;
import com.wellsoft.globo.assinaturas.application.port.output.RecurrenceOutput;
import com.wellsoft.globo.assinaturas.infrastructure.config.AbstractRabbitMQConfiguration;
import com.wellsoft.globo.assinaturas.infrastructure.config.ListenerMQConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecurrenceProducer implements RecurrenceOutput {

    private final RabbitTemplate rabbitTemplate;
    private final ListenerMQConfiguration.MessageProperties messageProperties;
    private AbstractRabbitMQConfiguration.MessageConfiguration configuration;

    @PostConstruct
    private void postConstruct() {
        configuration = messageProperties.getCreateRecurrence();
    }

    @Override
    public void createRecurrence(CreateRecurrenceDto createRecurrenceDto) {
        log.info("Send to created recurrence {}", createRecurrenceDto);
        rabbitTemplate.convertAndSend(configuration.getExchangeName(), configuration.getQueueName(), createRecurrenceDto);
    }
}
