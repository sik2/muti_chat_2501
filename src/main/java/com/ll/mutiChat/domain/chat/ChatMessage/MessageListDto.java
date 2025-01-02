package com.ll.mutiChat.domain.chat.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageListDto {
    private List<ChatMessageResponseDto> messages;
    private int totalCount;
}
