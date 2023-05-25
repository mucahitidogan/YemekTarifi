package com.yemektarifi.rabbitmq.producer;

import com.yemektarifi.rabbitmq.model.ActivateCodeMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateCodeMailProducer {

    @Value("${rabbitmq.exchange-auth}")
    String exchange;
    @Value("${rabbitmq.activateKey}")
    String activateBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendActivationCode(ActivateCodeMailModel activateCodeMailModel){
        rabbitTemplate.convertAndSend(exchange, activateBindingKey, activateCodeMailModel);
    }
}
