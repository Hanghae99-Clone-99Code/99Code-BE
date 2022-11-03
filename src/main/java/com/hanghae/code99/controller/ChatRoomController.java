package com.hanghae.code99.controller;

import com.hanghae.code99.controller.request.RoomRequestDto;
import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.domain.message.RoomRequestConverter;
import com.hanghae.code99.jwt.userdetails.UserDetailsImpl;
import com.hanghae.code99.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomController {
    private final ChatRoomService chatService;

    private final RoomRequestConverter roomRequestConverter;
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

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    public ResponseDto<?> roomInfo(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.findOneRoom(roomId, userDetails);
    }
    // 채팅방 수정
    @PutMapping(value = "/room/{roomId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> updateRoom(
            @PathVariable Long roomId, // 수정하고자 하는 Room의 고유 ID
            @RequestPart(value = "media", required = false) MultipartFile multipartFiles, // 수정할 미디어 파일
            @RequestPart(value = "postDto") String requestDto, // 수정 정보
            HttpServletRequest request) { // 현재 로그인한 유저의 인증 정보를 확인하기 위한 HttpServletRequest
        RoomRequestDto convertedDto = roomRequestConverter.convert(requestDto);
        return chatService.updateRoom(roomId, convertedDto, multipartFiles, request);
    }

//    @PostMapping("/room/{roomId}")
//    public ResponseDto<?> createPost(@RequestPart("data") RoomRequestDto requestDto,
//                                     HttpServletRequest request,@RequestPart("image") MultipartFile images){
//        try {
//            return chatService.updateRoom(requestDto,request, images);
//        } catch (IOException e) {
//            throw new RuntimeException("error");
//        }
//    }
}
