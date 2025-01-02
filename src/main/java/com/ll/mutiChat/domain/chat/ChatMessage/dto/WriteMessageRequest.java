package com.ll.mutiChat.domain.chat.ChatMessage.dto;

import lombok.Getter;
import org.springframework.stereotype.Component;

public record WriteMessageRequest(String writerName, String content) {
}
