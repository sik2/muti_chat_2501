package com.ll.mutiChat.domain.chat.ChatMessage.dto;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;

import java.util.List;

public record GetMessageResponse(List<ChatMessage> messages) {
}
