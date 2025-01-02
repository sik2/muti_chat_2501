package com.ll.mutiChat.domain.chat.ChatRoom.service;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    public void makeRoom(String roomName) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(roomName)
                .build();

        chatRoomRepository.save(chatRoom);
    }

    public ChatRoom findById(long roomId){
        return chatRoomRepository.findById(roomId).orElseThrow();
    }

    public List<ChatMessage> findMessageByRoomId(long roomId){
        return chatRoomRepository.findById(roomId).orElseThrow().getChatMessages();
    }
}
