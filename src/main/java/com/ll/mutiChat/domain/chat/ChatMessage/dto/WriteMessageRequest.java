package com.ll.mutiChat.domain.chat.ChatMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class WriteMessageRequest {
    String writerName;
    String content;
}
