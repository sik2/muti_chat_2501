package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.dto.ChatMessageResponseDto;
import com.ll.mutiChat.domain.chat.ChatRoom.dto.ChatRoomResponseDto;
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

        //굳이 생성일자나 수정일자를 담을 필요는 없으므로, DTO 형태로 채팅방 전체를 모델에 담아 전달한다.
        List<ChatRoomResponseDto> dtoList= chatRooms.stream()
                .map(room->new ChatRoomResponseDto(room.getId(), room.getName(),null))
                .collect(Collectors.toList());

        model.addAttribute("chatRooms", dtoList);
        return "domain/chat/chatRoom/list";
    }

    @GetMapping("/{roomId}")
    public String showRoom(@PathVariable Long roomId,Model model) {
        ChatRoom chatRoom = chatRoomService.findChatRoom(roomId);

        //채팅은 까딱하면 순환참조가 일어날 수도 있으므로 DTO로 변환한다.
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
    //RequestBody로 받아도 되고, 들어오는 게 하나 뿐인 경우이므로 생략해도 된다.
    public String makeRoom(@RequestParam String name) {
        Long id = chatRoomService.createChatRoom(name).getId();
        return "redirect:/chat/room/"+id;
    }





}
