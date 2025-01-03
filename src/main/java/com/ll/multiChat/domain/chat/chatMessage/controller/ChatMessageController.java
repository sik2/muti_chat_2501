package com.ll.multiChat.domain.chat.chatMessage.controller;


import com.ll.multiChat.domain.chat.chatMessage.service.ChatMessageService;
import com.ll.multiChat.domain.chat.chatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ChatMessageController {

  private final ChatMessageService chatMessageService;
  private final ChatRoomService chatRoomService;

}
