package com.example.demo.chat;

import com.example.demo.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    // roomId로 모든 채팅 조회
    List<Chat> findByRoomId(Long roomId);

    // roomId와 chatId 기준으로 이후 채팅 조회
    List<Chat> findAllByRoomIdAndIdGreaterThan(Long roomId, Long chatId);
}
