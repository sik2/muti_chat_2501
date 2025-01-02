package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {

    Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
    private final ChatRoomService chatRoomService;

    // 채팅방 목록 화면 이동
    @GetMapping("/list")
    public String chatRoomList(Model model) {
        List<ChatRoom> chatRooms = new ArrayList<>();
        // 모든 채팅방 목록 출력
        chatRooms = chatRoomService.showRoomList();

        model.addAttribute("chatRooms", chatRooms);
        return "domain/chat/chatRoom/list";
    }

    // 채팅방 입장 화면 이동
    @GetMapping("/{roomId}")
    public String showRoom(@PathVariable long roomId,
                           @RequestParam(defaultValue = "NoName") String chatRoomName,
                           Model model) {

        model.addAttribute("roomId", roomId);
        model.addAttribute("chatRoomName", chatRoomName);

        return "domain/chat/chatRoom/room";
    }

    // 채팅방 생성 화면 이동
    @GetMapping("/make")
    public String makeRoom() {
        return "domain/chat/chatRoom/make";
    }

    @PostMapping("/make")
    public String makeRoom(@RequestParam String name) {

        try {
            // 채팅방 생성
            chatRoomService.makeRoom(name);
            logger.info("채팅방 생성 완료");
            return "redirect:/chat/room/list";
        } catch (Exception e) {
            logger.error("채팅방 생성 실패", e);
            return "redirect:/chat/room/list";
        }

    }

}
