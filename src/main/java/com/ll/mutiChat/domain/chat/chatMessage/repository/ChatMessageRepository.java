package com.ll.mutiChat.domain.chat.chatMessage.repository;

import com.ll.mutiChat.domain.chat.chatMessage.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
