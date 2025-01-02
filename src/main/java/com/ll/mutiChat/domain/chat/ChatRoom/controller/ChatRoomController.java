package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/{roomId}")
    @ResponseBody
    public String showRoom(@PathVariable long roomId, @RequestParam(defaultValue = "NoName") String writerName) {
        return String.format("%d 번 채팅방 입니다", roomId, writerName);
    }

    @GetMapping("/make")
    public String makeRoom() {
        return "domain/chat/chatRoom/make";
    }

    @PostMapping("/make")
    public String makeRoom(@RequestParam String name) {
        chatRoomService.makeRoom(name);

        return "redirect:/chat/room/list";
    }

    @GetMapping("/list")
    public String listRoom(Model model) {
        model.addAttribute("rooms", chatRoomService.getRooms());
        return "domain/chat/chatRoom/list";
    }


}
