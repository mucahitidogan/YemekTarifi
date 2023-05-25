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


    @Value("${rabbitmq.exchange-userProfile}")
    String exchange;
    @Value("${rabbitmq.favorite-userProfile-key}")
    String favoriteBindingKey;
    @Value("${rabbitmq.queue-favorite}")
    String queueNameFavorite;


    @Bean
    DirectExchange exchangeUserProfile(){
        return new DirectExchange(exchange);
    }

    @Bean
    Queue favoriteQueue(){
        return new Queue(queueNameFavorite);
    }

    @Bean
    public Binding bindingRegister(final Queue favoriteQueue, final DirectExchange exchangeUserProfile){
        return BindingBuilder.bind(favoriteQueue).to(exchangeUserProfile).with(favoriteBindingKey);
    }

}
