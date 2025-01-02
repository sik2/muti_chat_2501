package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("list")
    public String list(Model model){
        model.addAttribute("chatRooms",chatRoomService.findAll());
        return "domain/chat/chatRoom/list";
    }

    @GetMapping("{roomId}")
    public String showRoom(
        @PathVariable long roomId,
        Model model) {
        model.addAttribute("chatRoom", chatRoomService.findById(roomId));
        return "domain/chat/chatRoom/room";
    }

    @GetMapping("make")
    public String makeRoomForm() {
        return "domain/chat/chatRoom/make";
    }

    @PostMapping("make")
    public String makeRoom(@RequestParam String name){
        chatRoomService.makeChatRoom(name);
        return "redirect:list";
    }

}
