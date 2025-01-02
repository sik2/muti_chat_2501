package com.ll.mutiChat.domain.chat.ChatRoom.service;

import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public void makeRoom(ChatRoom chatRoom) {
        // 채팅방 생성
        chatRoomRepository.save(chatRoom);

    }

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }
}
