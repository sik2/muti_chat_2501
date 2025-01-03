package com.ll.mutiChat.domain.chat.ChatMessage.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.repository.ChatMessageRepository;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private ChatMessageRepository chatMessageRepository;

    //jpa
    @GetMapping("/polling")
    public List<ChatMessage> polling(@RequestParam Long lastMessageId) {
        if (lastMessageId == null) {
            return chatMessageRepository.findAll();
        }
        //주어진 id 보다 더큰 대상을 모두 가져온다
        return chatMessageRepository.findByIdGreaterThan(lastMessageId);
    }
}
