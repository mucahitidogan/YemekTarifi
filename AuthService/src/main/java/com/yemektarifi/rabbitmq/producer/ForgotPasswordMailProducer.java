package com.yemektarifi.rabbitmq.producer;

import com.yemektarifi.rabbitmq.model.ActivateCodeMailModel;
import com.yemektarifi.rabbitmq.model.ForgotPasswordMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordMailProducer {

    @Value("${rabbitmq.exchange-auth}")
    String exchange;
    @Value("${rabbitmq.forgotPasswordBindingKey}")
    String forgotPasswordBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendForgotPasswordMail(ForgotPasswordMailModel forgotPasswordMailModel){
        rabbitTemplate.convertAndSend(exchange, forgotPasswordBindingKey, forgotPasswordMailModel);
    }
}
