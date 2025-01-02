package com.ll.mutiChat.domain.chat.ChatMessage.service;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.repository.ChatMessageRepository;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    public Long writeMessage(Long roomId, String writerName, String content) {
        ChatRoom target=chatRoomRepository.findById(roomId).orElseThrow();

        ChatMessage chatMessage=ChatMessage.builder()
                .chatRoom(target)
                .writerName(writerName)
                .content(content)
                .build();

        chatMessageRepository.save(chatMessage);

        return chatMessage.getId();
    }
}
