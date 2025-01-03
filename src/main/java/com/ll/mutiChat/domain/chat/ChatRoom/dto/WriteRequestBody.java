package com.ll.mutiChat.domain.chat.ChatRoom.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WriteRequestBody {
    private String writerName;
    private String content;
}