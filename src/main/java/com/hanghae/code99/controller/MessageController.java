package com.hanghae.code99.controller;

import com.hanghae.code99.controller.response.ChatMessageDto;
import com.hanghae.code99.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final KafkaProducerService producerService;

    @MessageMapping("/chat/message")
    public void enter(ChatMessageDto message) {
        producerService.sendMessage(message);
    }
}
