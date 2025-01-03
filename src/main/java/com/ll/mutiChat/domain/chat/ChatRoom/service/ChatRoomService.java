package com.ll.mutiChat.domain.chat.ChatRoom.service;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom findById(long roomId){
        return chatRoomRepository.findById(roomId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 방입니다."));
    }
    public void save(ChatRoom chatRoom){
        chatRoomRepository.save(chatRoom);
    }
    public List<ChatRoom> findAll(){
        return chatRoomRepository.findAll();
    }
    public void saveMessage(long roomId, ChatMessage chatMessage){
        ChatRoom ch = chatRoomRepository.findById(roomId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 방입니다."));
        ch.addChatMessage(chatMessage);
    }
}
