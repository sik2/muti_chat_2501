package com.ll.mutiChat.domain.chat.ChatRoom.controller;


import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatRoom.dto.MessagesRequest;
import com.ll.mutiChat.domain.chat.ChatRoom.dto.MessagesResponse;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private List<ChatMessage> chatMessages;
//    @GetMapping("/{roomId}")
//    @ResponseBody
//    public String showRoom(@PathVariable long roomId, @RequestParam(defaultValue = "NoName") String writerName) {
//
//        return String.format("%d 번 채팅방 입니다. writer : %s", roomId, writerName);
//    }

    @GetMapping("/make")
    public String mainRoom() {

        return "domain/chat/chatRoom/make";
    }
    @PostMapping("/make")
    public String makeRoom(@RequestParam String name){
        ChatRoom chatRoom = ChatRoom.builder().name(name).build();
        chatRoomService.save(chatRoom);
        return "redirect:/chat/room/make";
    }
    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("chatRooms", chatRoomService.findAll());
        return "domain/chat/chatRoom/list";
    }
    @GetMapping("/{roomId}")
    public String room(Model model,@PathVariable long roomId){
        long lastChatMessageId = chatMessages==null ?0:chatMessages.size();
        model.addAttribute("lastChatMessageId",lastChatMessageId);
        model.addAttribute("roomId",roomId);
        return "domain/chat/chatRoom/room";
    }
    @GetMapping("/messages")//해당 id인 유저가 보낸 메세지만 가져오기
    @ResponseBody
    public RsData<MessagesResponse> messages(MessagesRequest messagesRequest) {
        List<ChatMessage> messages = chatMessages;
        if(messagesRequest.fromId()!=null){

            int index = IntStream.range(0,messages.size())
                    .filter(i -> chatMessages.get(i).getId()==messagesRequest.fromId()
                            &&chatMessages.get(i).getChatRoom().getId()== messagesRequest.roomId())
                    .findFirst()
                    .orElse(-1);
            if (index != -1) {
                messages = messages.subList(index + 1, messages.size());
            }//
        }
        return  RsData.of("200", "메세지 가져오기 성공", new MessagesResponse(messages, messages.size()));
    }
}
