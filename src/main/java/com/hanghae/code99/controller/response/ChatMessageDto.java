package com.hanghae.code99.controller.response;

import com.hanghae.code99.domain.message.ChatMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    public enum MessageType {
        ENTER, TALK
    }
    private ChatMessage.MessageType type;
    //채팅방 ID
    private Long roomId;
    //보내는 사람
    private String sender;
    //내용
    private String message;
}
