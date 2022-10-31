package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.message.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface    ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
