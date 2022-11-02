package com.hanghae.code99.service;

import com.hanghae.code99.controller.response.ChatMessageDto;
import com.hanghae.code99.domain.message.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SimpMessageSendingOperations sendingOperations;

    @KafkaListener(topics = "code99", groupId = "code99", containerFactory = "kafkaListener")
    public void consume(ChatMessage message) {
        System.out.println("카프카 컨슈머 = " + message.getMember().getNickname());
        System.out.println(message);
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoom().getRoomId(), message );
    }
}

