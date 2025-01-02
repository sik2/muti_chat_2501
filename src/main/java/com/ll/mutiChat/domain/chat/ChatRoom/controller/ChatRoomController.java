package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.dto.*;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.rsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
@Slf4j
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    private List<ChatMessage> chatMessages = new ArrayList<>();

    @GetMapping("/list")
    public String list(Model model) {
        chatRoomService.showAllChatRoom();
        model.addAttribute("chatRooms", chatRoomService.showAllChatRoom());
        return "domain/chat/chatRoom/list";
    }

    @GetMapping("/make")
    public String makeRoom() {
        return "domain/chat/chatRoom/make";
    }

    @PostMapping("/make")
    public String makeRoom(String name) {
        chatRoomService.createChatRoom(name);
        return "redirect:/chat/room/list";
    }

    @GetMapping("/{roomId}")
    public String showRoom(@PathVariable long roomId, @RequestParam(defaultValue = "NoName") String writerName, Model model) {
        ChatRoom room = chatRoomService.findById(roomId).get();
        model.addAttribute("room", room);
        return "domain/chat/chatRoom/room";
    }

    @PostMapping(value = "/{roomId}/write")
    @ResponseBody
    public RsData<WriteMessageResponse> write(@PathVariable long roomId, @RequestBody WriteMessageRequest writeMessageRequest) {
        ChatMessage chatMessage = chatRoomService.write(roomId, writeMessageRequest.writerName(), writeMessageRequest.content());
        return RsData.of("200", "메세지가 작성되었습니다.", new WriteMessageResponse(chatMessage.getId()));
    }


    @GetMapping("/{roomId}/messagesAfter/{afterId}")
    @ResponseBody
    public RsData<GetMessageResponse> getMessagesAfter(@PathVariable long roomId, @PathVariable long afterId) {
        List<ChatMessage> messages = chatMessageService.findByChatRoomIdAndIdAfter(roomId, afterId);
        return RsData.of("200", "메세지 가져오기 성공", new GetMessageResponse(messages));
    }

}
