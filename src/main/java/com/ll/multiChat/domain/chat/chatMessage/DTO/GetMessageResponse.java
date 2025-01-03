package com.ll.multiChat.domain.chat.chatMessage.DTO;

import com.ll.multiChat.domain.chat.chatMessage.entity.ChatMessage;

import java.util.List;

public record GetMessageResponse(List<ChatMessage> messages) {
}
