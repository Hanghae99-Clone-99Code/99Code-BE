package com.hanghae.code99.controller;

import com.hanghae.code99.controller.request.RoomRequestDto;
import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.jwt.userdetails.UserDetailsImpl;
import com.hanghae.code99.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomController {
    private final ChatRoomService chatService;

    // 본인이 속한 채팅방 목록 반환
    @GetMapping("/room")
    public ResponseDto<?> room(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.findAllRoom(userDetails);
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseDto<?> createRoom(@RequestBody RoomRequestDto roomRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.createRoom(roomRequestDto, userDetails);
    }


    // 채팅방 생성
    @Transactional
    @DeleteMapping("/room/{roomId}")
    public ResponseDto<?> removeRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.removeRoom(roomId, userDetails);
    }

    // 채팅방 생성
    @PostMapping("/invite/{roomId}")
    public ResponseDto<?> inviteRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.inviteRoom(roomId, userDetails);
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseDto<?> roomInfo(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.findOneRoom(roomId, userDetails);
    }
}
