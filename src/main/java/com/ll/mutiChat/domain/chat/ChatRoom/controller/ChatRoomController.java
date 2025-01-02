package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatRoom.dto.ChatRoomDto;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/make")
    public String showMakeRoom(Model model) {
        model.addAttribute("chatRoomDto", new ChatRoomDto());
        return "domain/chat/chatRoom/make";
    }

    @PostMapping("/make")
    public String makeRoom(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = ChatRoom.create(chatRoomDto.getName());
        chatRoomService.makeRoom(chatRoom);

        return "domain/chat/chatRoom/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("chatRooms", chatRoomService.findAll());
        return "domain/chat/chatRoom/list";
    }

}
