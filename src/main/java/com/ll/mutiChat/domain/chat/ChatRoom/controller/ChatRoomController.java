package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.dto.WriteRequestBody;
import com.ll.mutiChat.domain.chat.ChatRoom.dto.WriteResponseBody;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{roomId}")
    public String showRoom(
            @PathVariable final long roomId,
            final String writerName,
            Model model
    ) {
        ChatRoom room = chatRoomService.findById(roomId).get();
        model.addAttribute("room", room);

        return "domain/chat/chatRoom/room";
    }

    @GetMapping("/make")
    public String showMake() {
        return "domain/chat/chatRoom/make";
    }

    @PostMapping("/make")
    public String make(
            final String name
    ) {
        chatRoomService.make(name);

        return "redirect:/chat/room/list";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<ChatRoom> chatRooms = chatRoomService.getList();
        model.addAttribute("chatRooms", chatRooms);

        return "domain/chat/chatRoom/list";
    }

    @PostMapping("/{roomId}/write")
    @ResponseBody
    public RsData<?> write(
            @PathVariable final long roomId,
            @RequestBody final WriteRequestBody requestBody
    ) {
        ChatRoom chatRoom = chatRoomService.findById(roomId).get();
        ChatMessage chatMessage = chatMessageService.write(chatRoom, requestBody.getWriterName(), requestBody.getContent());

        RsData<WriteResponseBody> writeRs = RsData.of("S-1", "%d번 메시지를 작성하였습니다.".formatted(chatMessage.getId()), new WriteResponseBody(chatMessage));

        messagingTemplate.convertAndSend("/topic/chat/room/" + roomId + "/messageCreated", writeRs);

        return RsData.of("S-1", "성공");
    }
}