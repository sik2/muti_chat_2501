package com.ll.mutiChat.domain.chat.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatMessageResponseDto {
    private Long id;
    private String writerName;
    private String content;
}
