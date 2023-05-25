package com.yemektarifi.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    @Value("${rabbitmq.exchange-auth}")
    String exchange;
    @Value("${rabbitmq.activateKey}")
    String activateBindingKey;
    @Value("${rabbitmq.queueActivate}")
    String queueNameActivate;

    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }

    @Bean
    Queue activateQueue(){
        return new Queue(queueNameActivate);
    }

    @Bean
    public Binding bindingRegister(final Queue activateQueue, final DirectExchange exchangeAuth){
        return BindingBuilder.bind(activateQueue).to(exchangeAuth).with(activateBindingKey);
    }


    @Value("${rabbitmq.forgotPasswordQueue}")
    private String forgotPasswordQueue;
    @Value("${rabbitmq.forgotPasswordBindingKey}")
    private String forgotPasswordBindingKey;

    @Bean
    Queue forgotPasswordQueue(){
        return new Queue(forgotPasswordQueue);
    }

    @Bean
    public Binding bindingForgotPassword(final Queue forgotPasswordQueue, final DirectExchange exchangeAuth){
        return BindingBuilder.bind(forgotPasswordQueue).to(exchangeAuth).with(forgotPasswordBindingKey);
    }

}
