package com.hanghae.code99.controller;

import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.domain.message.ChatRoom;
import com.hanghae.code99.jwt.userdetails.UserDetailsImpl;
import com.hanghae.code99.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomService chatService;

    // 본인이 속한 채팅방 목록 반환
    @GetMapping("/room")
    public ResponseDto<?> room(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.findAllRoom(userDetails);
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseDto<?> createRoom(@RequestParam String name, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(chatService.createRoom(name, userDetails));
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseDto<?> roomInfo(@PathVariable Long roomId) {
        return chatService.findById(roomId);
    }
}
