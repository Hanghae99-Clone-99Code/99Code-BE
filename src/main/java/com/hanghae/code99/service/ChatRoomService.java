package com.hanghae.code99.service;

import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.message.ChatRoom;
import com.hanghae.code99.jwt.userdetails.UserDetailsImpl;
import com.hanghae.code99.repositrory.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    //채팅방 불러오기
    public ResponseDto<?> findAllRoom(UserDetailsImpl userDetails) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByMembersContains(userDetails.getMember());
        return ResponseDto.success(chatRoom);
    }

    //채팅방 하나 불러오기
    public ResponseDto<?> findById(Long roomId) {
        return ResponseDto.success(chatRoomRepository.findById(roomId));
    }

    //채팅방 생성
    public ChatRoom createRoom(String name, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        ChatRoom chatRoom = new ChatRoom(name, member);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }
}
