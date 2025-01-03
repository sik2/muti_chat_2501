package com.ll.mutiChat.domain.chat.ChatRoom.dto;


import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessagesResponse {
    private List<ChatMessage> chatMessages;
    private int totalCount;
}