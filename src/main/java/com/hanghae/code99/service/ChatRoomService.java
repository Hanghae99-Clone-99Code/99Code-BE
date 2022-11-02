package com.hanghae.code99.service;

import com.hanghae.code99.controller.request.RoomRequestDto;
import com.hanghae.code99.controller.response.MemberResponseDto;
import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.controller.response.RoomResponseDto;
import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.message.ChatMessage;
import com.hanghae.code99.domain.message.Room;
import com.hanghae.code99.domain.message.RoomMember;
import com.hanghae.code99.jwt.userdetails.UserDetailsImpl;
import com.hanghae.code99.repositrory.ChatMessageRepository;
import com.hanghae.code99.repositrory.RoomMemberRepository;
import com.hanghae.code99.repositrory.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;

    private final ChatMessageRepository chatMessageRepository;

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
    public ResponseDto<?> findOneRoom(Long roomId, UserDetailsImpl userDetails) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            return ResponseDto.fail("asd","존재하지 않는 채팅방입니다.");
        }
        List<RoomMember> roomMember = roomMemberRepository.findAllByRoom(room);
        List<Member> memberList = roomMember.stream()
                .map(RoomMember::getMember)
                .collect(Collectors.toList());
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByRoom(room.get());
        RoomResponseDto roomResponseDto = RoomResponseDto
                .builder()
                .roomId(room.get().getRoomId())
                .roomName(room.get().getRoomName())
                .description(room.get().getDescription())
                .imageUrl(room.get().getImageUrl())
                .memberList(memberList)
                .chatMessageList(chatMessageList)
                .build();
        return ResponseDto.success(roomResponseDto);
    }

    //채팅방 초대
    public ResponseDto<?> inviteRoom(Long roomId, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            return ResponseDto.fail("asd","존재하지 않는 채팅방입니다.");
        }
        RoomMember roomMember = RoomMember.builder()
                .room(room.get())
                .member(member)
                .build();
        roomMemberRepository.save(roomMember);
        return ResponseDto.success("채팅방에 초대되었습니다.");
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
