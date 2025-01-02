package com.ll.mutiChat.domain.chat.ChatRoom.repository;

import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
