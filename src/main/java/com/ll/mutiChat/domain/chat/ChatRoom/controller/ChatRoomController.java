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

    @GetMapping("/list")
    public String listRoom(Model model) {
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        model.addAttribute("chatRooms", chatRooms);
        return "domain/chat/chatRoom/list";
    }

    @GetMapping("/make")
    public String makeRoom() {
        return "domain/chat/chatRoom/make";
    }

    @PostMapping("/make")
    public String makeRoom(@RequestParam String roomName) {
        chatRoomService.makeRoom(roomName);
        return "redirect:/chat/room/list";
    }

    @GetMapping("/{roomId}")
    @ResponseBody
    public String showRoom(@PathVariable long roomId, @RequestParam(defaultValue = "NoName") String writerName) {
        return String.format("%d 번 채팅방 입니다. writer : %s", roomId, writerName);
    }

    //@PostMapping("/{roomId}/make")

    //@GetMapping("/{roomId}/messageAfter/{AfterId}")
}
