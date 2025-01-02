package com.ll.mutiChat.domain.chat.ChatMessage.service;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.repository.ChatMessageRepository;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import com.ll.mutiChat.domain.chat.dto.WriteMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomRepository chatRoomRepository;

  public ChatMessage writeMessage(WriteMessageRequest writeMessageRequest) {
    ChatRoom chatRoom = chatRoomRepository.findById(writeMessageRequest.getChatRoomId())
        .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

    ChatMessage chatMessage = ChatMessage.builder()
        .chatRoom(chatRoom)
        .writerName(writeMessageRequest.getWriterName())
        .content(writeMessageRequest.getContent())
        .build();

    return chatMessageRepository.save(chatMessage);
  }
}
