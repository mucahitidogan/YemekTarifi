package com.yemektarifi.rabbitmq.consumer;

import com.yemektarifi.rabbitmq.model.ActivateCodeMailModel;
import com.yemektarifi.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivateCodeMailConsumer {

    private final MailService mailService;

    @RabbitListener(queues = ("${rabbitmq.queueActivate}"))
    public void sendActivationCode(ActivateCodeMailModel activateCodeMailModel) {
        log.info("Mail {}", activateCodeMailModel.toString());
        mailService.sendActivateCode(activateCodeMailModel);
    }
}
