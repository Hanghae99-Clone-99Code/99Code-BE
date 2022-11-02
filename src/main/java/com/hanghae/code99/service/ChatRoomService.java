package com.hanghae.code99.service;

import com.hanghae.code99.controller.request.RoomRequestDto;
import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.controller.response.RoomResponseDto;
import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.message.Room;
import com.hanghae.code99.domain.message.RoomMember;
import com.hanghae.code99.jwt.userdetails.UserDetailsImpl;
import com.hanghae.code99.repositrory.RoomMemberRepository;
import com.hanghae.code99.repositrory.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;

    //채팅방 불러오기
    public ResponseDto<?> findAllRoom(UserDetailsImpl userDetails) {
        List<RoomMember> roomMember = roomMemberRepository.findAllByMember(userDetails.getMember());
        List<RoomResponseDto> roomList = roomMember.stream()
                .map(RoomMember::getRoom)
                .map(room -> RoomResponseDto
                        .builder()
                        .roomId(room.getRoomId())
                        .roomName(room.getRoomName())
                        .description(room.getDescription())
                        .imageUrl(room.getImageUrl())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(roomList);
    }

    //채팅방 하나 불러오기
    public ResponseDto<?> findById(Long roomId) {
        return ResponseDto.success(roomRepository.findById(roomId));
    }

    //채팅방 생성
    public ResponseDto<?> createRoom(RoomRequestDto roomRequestDto, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Room room = Room.builder()
                .roomName(roomRequestDto.getRoomName())
                .imageUrl(roomRequestDto.getImageUrl())
                .description(roomRequestDto.getDescription())
                .build();
        roomRepository.save(room);
        roomMemberRepository.save(RoomMember.builder()
                .member(member)
                .room(room)
                .build());
        return ResponseDto.success(room);
    }
}
