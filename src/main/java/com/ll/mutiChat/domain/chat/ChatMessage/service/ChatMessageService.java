package com.ll.mutiChat.domain.chat.ChatMessage.service;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.repository.ChatMessageRepository;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void createMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findByIdGreaterThan(long roomId, long id) {
        return chatMessageRepository.findByIdGreaterThanAndRoomId(roomId, id);
    }

/*    public List<ChatMessage> findByIdGreaterThan(long id) {
        return chatMessageRepository.findByIdGreaterThan(id);
    }*/

    public void makeBasicMessageList(ChatRoom chatRoom1, String 사용자, String 내용) {
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom1)
                .writerName(사용자)
                .content(내용)
                .build();
        chatMessageRepository.save(chatMessage);
    }
}
