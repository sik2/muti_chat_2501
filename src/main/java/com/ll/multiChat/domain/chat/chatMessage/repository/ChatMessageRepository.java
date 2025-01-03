package com.ll.multiChat.domain.chat.chatMessage.repository;


import com.ll.multiChat.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.multiChat.domain.chat.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

  List<ChatMessage> findByChatRoomIdAndIdAfter(long roomId, long afterId);
}
