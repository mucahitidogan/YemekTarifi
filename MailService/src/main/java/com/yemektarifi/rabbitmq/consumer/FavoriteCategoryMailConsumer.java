package com.yemektarifi.rabbitmq.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yemektarifi.rabbitmq.model.FavoriteCategoryMailModel;
import com.yemektarifi.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FavoriteCategoryMailConsumer {

    private final MailService mailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = ("${rabbitmq.queue-favorite}"))
    public void sendFavoriteCategoryMail(String jsonModel) throws JsonProcessingException {
        FavoriteCategoryMailModel favoriteCategoryMailModel = objectMapper.readValue(jsonModel, FavoriteCategoryMailModel.class);
        log.info("Mail {}", jsonModel.toString());
        mailService.sendMailFavoriteCategory(favoriteCategoryMailModel);
    }
}
