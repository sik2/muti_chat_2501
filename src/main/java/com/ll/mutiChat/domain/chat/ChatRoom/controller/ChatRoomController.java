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
        System.out.println(chatRoomService.findAll());
        return "domain/chat/chatRoom/list";
    }

    @GetMapping("{roomId}")
    @ResponseBody
    public String showRoom(@PathVariable long roomId, @RequestParam(defaultValue = "NoName") String writerName) {

        return String.format("%d 번 채팅방 입니다. writer : %s", roomId, writerName);
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
