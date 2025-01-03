package com.ll.mutiChat.domain.chat.ChatRoom.dto;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WriteResponseBody {
    private ChatMessage message;
}