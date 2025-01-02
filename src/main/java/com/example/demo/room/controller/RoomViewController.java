package com.example.demo.room.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomViewController {

    @GetMapping("/rooms")
    public String getRoomsPage() {
        return "rooms";  // templates/rooms.html 반환
    }

    // 특정 채팅방 상세 페이지
    @GetMapping("/rooms/{id}")
    public String getRoomByIdPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roomId", id);  // roomId를 모델에 추가
        return "room-chats";  // templates/room-chats.html 반환
    }
}
