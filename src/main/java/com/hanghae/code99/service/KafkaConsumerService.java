package com.hanghae.code99.service;

import com.hanghae.code99.controller.response.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SimpMessageSendingOperations sendingOperations;

    @KafkaListener(topics = "code99", groupId = "code99", containerFactory = "kafkaListener")
    public void consume(ChatMessageDto message) {
        System.out.println("카프카 컨슈머 = " + message.getMessage());

        if (ChatMessageDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }

        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message );
    }
}

