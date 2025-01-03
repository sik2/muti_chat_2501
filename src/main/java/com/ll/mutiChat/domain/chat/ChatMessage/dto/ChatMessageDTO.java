package com.ll.mutiChat.domain.chat.ChatMessage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {

    private int chatRoomId;
    private String writerName;
    private String content;

}
