package com.hanghae.code99.service;

import com.hanghae.code99.controller.request.RoomRequestDto;
import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.controller.response.RoomResponseDto;
import com.hanghae.code99.domain.DefaultImages;
import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.message.ChatMessage;
import com.hanghae.code99.domain.message.Room;
import com.hanghae.code99.domain.message.RoomMember;
import com.hanghae.code99.jwt.TokenProvider;
import com.hanghae.code99.jwt.userdetails.UserDetailsImpl;
import com.hanghae.code99.repositrory.ChatMessageRepository;
import com.hanghae.code99.repositrory.RoomMemberRepository;
import com.hanghae.code99.repositrory.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;
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

    private final TokenProvider tokenProvider;

    private RoomMember roomMember;
    private FileUpdateService fileUpdateService;



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


    //채팅방 생성
    public ResponseDto<?> createRoom(RoomRequestDto roomRequestDto, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Room room = Room.builder()
                .roomName(roomRequestDto.getRoomName())
                .imageUrl(DefaultImages.getRandomRoomPic())
                .description(roomRequestDto.getDescription())
                .build();
        roomRepository.save(room);
        roomMemberRepository.save(RoomMember.builder()
                .member(member)
                .room(room)
                .build());
        return ResponseDto.success(room);
    }

    //채팅방 수정
    @Transactional
    public ResponseDto<?> updateRoom(
            Long roomId,
            RoomRequestDto requestDto,
            MultipartFile multipartFiles,
            HttpServletRequest request) {

        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) { //토큰 검증
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        Member member = tokenProvider.getMemberFromAuthentication(); //토큰에서 member정보 꺼내오기
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND","사용자를 찾을 수 없습니다.");
        }
        Room room = isPresentRoom(roomId); //보내준 roomId로 해당 room이 있는지 확인
        if (null == room) {
            return ResponseDto.fail("ROOM_NOT_FOUND","채팅방을 찾을 수 없습니다.");
        }
        roomMember = belongToRoom(roomId, member); //해당 roomId와 member로 이 멤버가 이 채팅방에 속한 유저인지 확인
        if (!room.inMember(roomMember)){
            return ResponseDto.fail("INVALID_MEMBER","채팅방에 속한 유저가 아닙니다.");
        }

        room.update(requestDto); //room에 roomName,description 넣어줌
        fileUpdateService.update(multipartFiles); //room에 imageUrl 넣어줌

        return ResponseDto.success(
                RoomRequestDto.builder()
                        .roomName(room.getRoomName())
                        .imageUrl(room.getImageUrl())
                        .description(room.getDescription())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public Room isPresentRoom(Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        return optionalRoom.orElse(null);
    }

    @Transactional(readOnly = true)
    public RoomMember belongToRoom(Long roomId, Member member) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId); //roomId로 해당 room을 불러옴
        Optional<RoomMember> optionalMember = roomMemberRepository.findByMemberbyRoom(member, optionalRoom); //해당 room과 토큰에서 추출한 멤버로 조회해서 RoomMember를 불러옴
        return optionalMember.orElse(null);
    }


}
