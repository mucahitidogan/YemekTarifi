package com.yemektarifi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.queueActivate}")
    String queueActivate;

    @Value("${rabbitmq.forgotPasswordQueue}")
    String forgotPasswordQueue;

    @Value("${rabbitmq.queue-favorite}")
    String queueNameFavorite;

    @Bean
    Queue queueActivate(){
        return new Queue(queueActivate);
    }

    @Bean
    Queue forgotPasswordQueue(){
        return new Queue(forgotPasswordQueue);
    }

    @Bean
    Queue queueNameFavorite(){
        return new Queue(queueNameFavorite);
    }




}
