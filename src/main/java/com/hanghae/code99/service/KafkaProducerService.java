package com.hanghae.code99.service;

import com.hanghae.code99.controller.response.ChatMessageDto;
import com.hanghae.code99.domain.message.ChatMessage;
import com.hanghae.code99.repositrory.ChatMessageRepository;
import com.hanghae.code99.repositrory.ChatRoomRepository;
import com.hanghae.code99.repositrory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private static final String TOPIC = "code99";
    private final KafkaTemplate<String, ChatMessageDto> kafkaTemplate;

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;
    public void sendMessage(ChatMessageDto chatMessageDto) {
        System.out.println("kafka producer : " + chatMessageDto.getMessage());

        ChatMessage chatMessage = ChatMessage.builder()
                .message(chatMessageDto.getMessage())
                .member(memberRepository.findByNickname(chatMessageDto.getSender()).get())
                .roomid(chatRoomRepository.findById(chatMessageDto.getRoomId()).get())
                .type(chatMessageDto.getType())
                .build();

        kafkaTemplate.send(TOPIC, chatMessageDto);
        chatMessageRepository.save(chatMessage);

    }
}
