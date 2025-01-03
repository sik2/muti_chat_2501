package com.ll.mutiChat.domain.chat.ChatMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ChatMessageRequestDto {
    private String writerName;
    private String content;
}
