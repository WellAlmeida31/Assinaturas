package com.wellsoft.globo.assinaturas.infrastructure.messaging.rabbitmq.consumer;

import com.wellsoft.globo.assinaturas.application.dto.CreateRecurrenceDto;
import com.wellsoft.globo.assinaturas.infrastructure.config.AbstractRabbitMQConfiguration;
import com.wellsoft.globo.assinaturas.infrastructure.config.ListenerMQConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecurrenceConsumer {


    @RabbitListener(containerFactory = ListenerMQConfiguration.CREATE_RECURRENCE,
            bindings = @QueueBinding(
                    value = @Queue(value = "${com.wellsoft.amqp.createRecurrence.queueName}", durable = "true"),
                    exchange = @Exchange(value = "${com.wellsoft.amqp.createRecurrence.exchangeName}", ignoreDeclarationExceptions = "true"),
                    key = "${com.wellsoft.amqp.createRecurrence.queueName}")
    )
    public void listenerCreatePaymentRecurrence(final CreateRecurrenceDto createRecurrenceDto) {
        log.info("Processing queue for assinaturas.createRecurrence {}", createRecurrenceDto);

    }
}
