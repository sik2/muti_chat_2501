package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.rsData.RsData;
import com.ll.mutiChat.global.sse.component.SseEmitters;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

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
    public String showRoom(@PathVariable long roomId,
                           @RequestParam(defaultValue = "NoName") String writerName, Model model) {
        model.addAttribute("roomId", roomId);
        model.addAttribute("room", chatRoomService.findById(roomId));

        return "domain/chat/chatRoom/room";
    }

    @PostMapping("/{roomId}/write")
    @ResponseBody
    public RsData<Void> writeMessage(@PathVariable long roomId,
                                     @RequestBody Map<String, String> payload) {
        String writerName = payload.get("writerName");
        String content = payload.get("content");
        chatMessageService.writeMessage(roomId, writerName, content);

        simpMessagingTemplate.convertAndSend("/topic/chat/writeMessage", payload);

        return RsData.of("S-1", "success");
    }

    @GetMapping("/{roomId}/messageAfter/{AfterId}")
    @ResponseBody
    public List<ChatMessage> getMessagesAfter(@PathVariable long roomId, @PathVariable long AfterId) {
        return chatMessageService.findMessagesAfter(roomId, AfterId);
    }
}
