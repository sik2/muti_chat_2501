package com.ll.mutiChat.domain.chat.dto;


import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WriteMessageResponse {
  private ChatMessage chatMessage;
}
