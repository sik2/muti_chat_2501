package com.ll.mutiChat.domain.chat.chatRoom.service;

import com.ll.mutiChat.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.chatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final Logger logger = Logger.getLogger(ChatRoomService.class.getName());
    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    public void makeRoom(String name) {
        logger.info("채팅방 생성");
        chatRoomRepository.save(ChatRoom.builder()
                .name(name)
                .build());
    }

    // 모든 채팅방 목록 조회
    public List<ChatRoom> showRoomList()  {
        logger.info("모든 채팅방 목록 조회");
        List<ChatRoom> chatRooms = new ArrayList<>();
        chatRooms = chatRoomRepository.findAll();

        System.out.println(" chatRooms = " + chatRooms);
        return chatRooms;
    }
}