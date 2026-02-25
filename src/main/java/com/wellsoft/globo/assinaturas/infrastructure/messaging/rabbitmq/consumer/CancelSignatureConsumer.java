package com.wellsoft.globo.assinaturas.infrastructure.messaging.rabbitmq.consumer;

import com.wellsoft.globo.assinaturas.application.dto.CancelSignatureDto;
import com.wellsoft.globo.assinaturas.application.dto.CreateRecurrenceDto;
import com.wellsoft.globo.assinaturas.infrastructure.config.ListenerMQConfiguration;
import com.wellsoft.globo.assinaturas.infrastructure.scheduler.CancelSignatureScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelSignatureConsumer {

    private final CancelSignatureScheduler cancelSignatureScheduler;

    @RabbitListener(containerFactory = ListenerMQConfiguration.CANCEL_SIGNATURE,
            bindings = @QueueBinding(
                    value = @Queue(value = "${com.wellsoft.amqp.cancelSignature.queueName}", durable = "true"),
                    exchange = @Exchange(value = "${com.wellsoft.amqp.cancelSignature.exchangeName}", ignoreDeclarationExceptions = "true"),
                    key = "${com.wellsoft.amqp.cancelSignature.queueName}")
    )
    public void listenerCreatePaymentRecurrence(final CancelSignatureDto cancelSignatureDto) {
        log.info("Processing queue for assinaturas.cancelSignature {}", cancelSignatureDto.getUserIdentifier());
        cancelSignatureScheduler.cancelSignatureAndRecurrence(cancelSignatureDto);
    }
}
