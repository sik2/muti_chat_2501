package com.ll.mutiChat.domain.chat.ChatMessage.dto;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

public record WriteMessageResponse (Long chatMessageId) {
}
