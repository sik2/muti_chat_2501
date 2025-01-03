package com.ll.mutiChat.domain.chat.ChatMessage.controller;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
}
