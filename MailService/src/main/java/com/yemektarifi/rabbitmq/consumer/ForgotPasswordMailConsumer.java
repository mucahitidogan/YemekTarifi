package com.yemektarifi.rabbitmq.consumer;

import com.yemektarifi.rabbitmq.model.ForgotPasswordMailModel;
import com.yemektarifi.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForgotPasswordMailConsumer {

    private final MailService mailService;

    @RabbitListener(queues = ("${rabbitmq.forgotPasswordQueue}"))
    public void sendForgotPasswordMail(ForgotPasswordMailModel forgotPasswordMailModel){
        log.info("Mail {}", forgotPasswordMailModel.toString());
        mailService.sendMailForgetPassword(forgotPasswordMailModel);
    }
}
