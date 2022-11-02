package com.hanghae.code99.controller.response;

import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.message.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {
    private Long roomId;
    private String roomName;
    private String imageUrl;
    private String description;
    private List<Member> memberList;
    private List<ChatMessage> chatMessageList;
}
