package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.ChatMessageResponseDto;
import com.ll.mutiChat.domain.chat.ChatRoom.ChatRoomResponseDto;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/list")
    public String showRoomList(Model model) {
        List<ChatRoom> chatRooms = chatRoomService.findAllChatRooms();
        List<ChatRoomResponseDto> dtoList= chatRooms.stream()
                .map(room->new ChatRoomResponseDto(room.getId(), room.getName(),null))
                .collect(Collectors.toList());

        model.addAttribute("chatRooms", dtoList);
        return "domain/chat/chatRoom/list";
    }

    @GetMapping("/{roomId}")
    public String showRoom(@PathVariable Long roomId,Model model) {
        ChatRoom chatRoom = chatRoomService.findChatRoom(roomId);

        List<ChatMessageResponseDto> messages = chatRoom.getChatMessages()
                .stream()
                .map(message -> new ChatMessageResponseDto(message.getId(), message.getWriterName(), message.getContent()))
                .collect(Collectors.toList());

        ChatRoomResponseDto dto = new ChatRoomResponseDto(chatRoom.getId(), chatRoom.getName(), messages);

        model.addAttribute("roomId",roomId);
        model.addAttribute("room",dto);
        return "domain/chat/chatRoom/room";
    }

    @GetMapping("/make")
    public String makeRoom() {
        return "domain/chat/chatRoom/make";
    }

    @PostMapping("/make")
    public String makeRoom(@RequestParam String name) {
        Long id = chatRoomService.createChatRoom(name);
        return "redirect:/chat/room/"+id;
    }





}
