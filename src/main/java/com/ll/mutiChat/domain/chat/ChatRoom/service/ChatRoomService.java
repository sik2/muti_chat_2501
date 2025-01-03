package com.ll.mutiChat.domain.chat.ChatRoom.service;

import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    public boolean save(ChatRoom ChatRoom) {
        try {
            chatRoomRepository.save(ChatRoom);
            return true;
        }catch (Exception e){
            System.out.println("에러발생" + e);
            return false;
        }
    }

    //서버 환경에 따라 사전에 DB에 데이터를 삽입하는 코드
    public ChatRoom make(String room) {

        ChatRoom chatRoom = ChatRoom.builder()
                .name(room)
                .build();

        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }
}
