package com.wellsoft.globo.assinaturas.infrastructure.messaging.rabbitmq.producer;

import com.wellsoft.globo.assinaturas.application.dto.CancelSignatureDto;
import com.wellsoft.globo.assinaturas.application.port.output.CancelSignatureOutput;
import com.wellsoft.globo.assinaturas.infrastructure.config.AbstractRabbitMQConfiguration;
import com.wellsoft.globo.assinaturas.infrastructure.config.ListenerMQConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelSignatureProducer implements CancelSignatureOutput {

    private final RabbitTemplate rabbitTemplate;
    private AbstractRabbitMQConfiguration.MessageConfiguration configurationCancel;
    private final ListenerMQConfiguration.MessageProperties messageProperties;

    @PostConstruct
    private void postConstruct() {
        configurationCancel = messageProperties.getCancelSignature();
    }

    @Override
    public void sendCancelPaymentRecurrenceAndSignature(CancelSignatureDto cancelSignatureDto) {
        log.info("Send to cancel signature {}", cancelSignatureDto.getUserIdentifier());
        rabbitTemplate.convertAndSend(configurationCancel.getExchangeName(), configurationCancel.getQueueName(), cancelSignatureDto);
    }
}
