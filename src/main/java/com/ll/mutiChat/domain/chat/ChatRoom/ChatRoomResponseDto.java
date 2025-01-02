package com.ll.mutiChat.domain.chat.ChatRoom;

import com.ll.mutiChat.domain.chat.ChatMessage.ChatMessageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponseDto {
    private Long id;
    private String name;
    private List<ChatMessageResponseDto> chatMessages;
}
