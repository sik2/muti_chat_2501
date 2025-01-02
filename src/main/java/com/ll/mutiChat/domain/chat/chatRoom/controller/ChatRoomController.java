package com.ll.mutiChat.domain.chat.chatRoom.controller;

import com.ll.mutiChat.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.chatRoom.service.ChatRoomService;
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
        logger.info("채팅방 목록 화면 이동");
        List<ChatRoom> chatRooms = new ArrayList<>();

        chatRooms = chatRoomService.showRoomList(); // 모든 채팅방 목록 출력

        model.addAttribute("chatRooms", chatRooms);
        return "domain/chat/chatRoom/list";
    }

    // 채팅방 입장
    @GetMapping("/{roomId}")
    public String showRoom(@PathVariable long roomId,
                           @RequestParam(defaultValue = "NoName") String chatRoomName,
                           Model model) {
        logger.info("채팅방 입장, 화면 이동");
        model.addAttribute("roomId", roomId);
        model.addAttribute("chatRoomName", chatRoomName);

        return "domain/chat/chatRoom/room";
    }

    // 채팅방 생성 화면 이동
    @GetMapping("/make")
    public String makeRoom() {
        logger.info("채팅방 생성 화면 이동");
        return "domain/chat/chatRoom/make";
    }

    // 채팅방 생성
    @PostMapping("/make")
    public String makeRoom(@RequestParam String name) {
        logger.info("채팅방 생성 요청");
        try {
            chatRoomService.makeRoom(name); // 채팅방 생성
            logger.info("채팅방 생성 완료");
            return "redirect:/chat/room/list";
        } catch (Exception e) {
            logger.error("채팅방 생성 실패", e);
            return "redirect:/chat/room/list";
        }
    }
}
