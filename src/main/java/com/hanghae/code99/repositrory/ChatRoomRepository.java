package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.message.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface    ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByMembersContains(Member member);

}
