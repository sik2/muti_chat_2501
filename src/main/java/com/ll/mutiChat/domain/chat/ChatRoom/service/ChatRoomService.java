package com.ll.mutiChat.domain.chat.ChatRoom.service;

import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> findAll(){
        return chatRoomRepository.findAll();
    }

    public ChatRoom makeChatRoom(String name){
        return chatRoomRepository.save(ChatRoom.builder()
            .name(name)
            .build());
    }

    public ChatRoom findById(long roomId) {
        return chatRoomRepository.findById(roomId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));
    }
}
