package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.message.ChatMessage;
import com.hanghae.code99.domain.message.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByRoom(Room room);
}
