package com.ll.mutiChat.domain.chat.ChatMessage.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.dto.ChatMessageDto;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @GetMapping("/chat/room/{roomId}")
    public String showRoom(@PathVariable long roomId, Model model) {
        List<ChatMessage> chatMessages = chatMessageService.findByRoomId(roomId);
        model.addAttribute("chatMessageDto", new ChatMessageDto());
        model.addAttribute("chatMessages", chatMessages);
        model.addAttribute("roomId", roomId);

        return "domain/chat/chatRoom/room";
    }

    @PostMapping("/chat/room/{roomId}/write")
    public String sendMessage(@PathVariable long roomId, ChatMessageDto chatMessageDto, Model model) {
        model.addAttribute("chatMessageDto", chatMessageDto);

        chatMessageService.saveMessage(roomId, chatMessageDto);

        return "domain/chat/chatRoom/room/" + roomId;
    }
}
