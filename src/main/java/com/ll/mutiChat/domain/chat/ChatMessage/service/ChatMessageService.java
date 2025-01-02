package com.ll.mutiChat.domain.chat.ChatMessage.service;

import com.ll.mutiChat.domain.chat.ChatMessage.dto.ChatMessageDto;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.repository.ChatMessageRepository;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatMessage> findByRoomId(long roomId) {
        return chatMessageRepository.findByRoomId(roomId);
    }

    public void saveMessage(long roomId, ChatMessageDto chatMessageDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId);
        ChatMessage chatMessage = ChatMessage.builder()
                .writerName(chatMessageDto.getWriterName())
                .content(chatMessageDto.getContent())
                .roomId(roomId)
                .build();

        chatMessage.setChatRoom(chatRoom);

        chatMessageRepository.save(chatMessage);
    }
}
