package com.ll.mutiChat.domain.chat.ChatMessage.service;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.repository.ChatMessageRepository;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public void writeMessage(long roomId, String writerName, String content) {
        ChatRoom chatRoom = chatRoomService.findById(roomId);
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .writerName(writerName)
                .content(content)
                .build();
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findMessagesAfter(long roomId, long lastMessageId) {
        return chatMessageRepository.findByChatRoomIdAndIdGreaterThan(roomId, lastMessageId);
    }
}
