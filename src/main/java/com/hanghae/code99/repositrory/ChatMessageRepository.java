package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.message.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
