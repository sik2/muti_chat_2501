package com.ll.mutiChat.domain.chat.ChatMessage.repository;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByIdGreaterThan(Long id);
    List<ChatMessage> findByIdGreaterThanAndRoomId(Long roomId, Long id);

}
