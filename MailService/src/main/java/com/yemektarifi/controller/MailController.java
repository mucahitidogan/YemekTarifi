package com.yemektarifi.controller;

import com.yemektarifi.rabbitmq.model.ActivateCodeMailModel;
import com.yemektarifi.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    @PostMapping("activate-code")
    public ResponseEntity<Boolean> forgotPasswordMail(@RequestBody ActivateCodeMailModel activateCodeMailModel){
        return ResponseEntity.ok(mailService.sendActivateCode(activateCodeMailModel));
    }
}
