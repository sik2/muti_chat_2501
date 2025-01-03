package com.ll.mutiChat.domain.chat.ChatMessage.repository;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByIdGreaterThan(Long id);

    @Query("SELECT m FROM ChatMessage m WHERE m.chatRoom.id = :roomId AND m.id > :id")
    List<ChatMessage> findByRoomIdAndIdGreaterThan(@Param("roomId") Long roomId, @Param("id") Long id);

}
