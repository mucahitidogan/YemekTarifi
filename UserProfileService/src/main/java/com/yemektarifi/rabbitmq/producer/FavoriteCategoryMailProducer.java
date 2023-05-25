package com.yemektarifi.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yemektarifi.rabbitmq.model.FavoriteCategoryMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteCategoryMailProducer {

    @Value("${rabbitmq.exchange-userProfile}")
    String exchange;
    @Value("${rabbitmq.favorite-userProfile-key}")
    String favoriteBindingKey;
    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendFavoriteRecipeCategory(FavoriteCategoryMailModel model){
        try {
            String jsonModel = objectMapper.writeValueAsString(model);
            rabbitTemplate.convertAndSend(exchange, favoriteBindingKey, jsonModel);
        }catch (JsonProcessingException e){
            e.getMessage();
        }

    }
}
