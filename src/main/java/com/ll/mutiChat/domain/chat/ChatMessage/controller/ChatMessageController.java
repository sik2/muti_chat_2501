package com.ll.mutiChat.domain.chat.ChatMessage.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.dto.WriteMessageRequest;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.domain.chat.Sse.util.SseEmitters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    private List<ChatMessage> chatMessages = new ArrayList<>();
    private final SseEmitters sseEmitters; //sse 방식으로 해보기
    @ResponseBody
    @GetMapping("/{roomId}")
    String chatRoomInfo(@PathVariable long roomId,
                        @RequestParam(defaultValue = "NoName") String writerName){


        return String.format("%d번 방입니다. writer : %s",roomId,writerName);
    }

    @GetMapping("/make")
    String chatRoomInfo(){

        return "domain/chat/chatRoom/make.html";
    }

    @PostMapping("/chat/room/{roomId}/write")
    public String write(@PathVariable long roomId,@RequestBody WriteMessageRequest writeMessageRequest){
        ChatRoom ch = chatRoomService.findById(roomId);
        ChatMessage cm = ChatMessage.builder().chatRoom(ch)
        .writerName(writeMessageRequest.getWriterName()).content(writeMessageRequest.getContent()).build();

        sseEmitters.noti("chat__messageAdded");


        chatRoomService.saveMessage(roomId,cm);
        return "redirect:/chat/room/"+roomId;
    }

    @GetMapping("/chat/room/{roomId}/messagesAfter/{lastChatMessageId}")
    public String messages(Model model, @PathVariable Long roomId, @PathVariable Long lastChatMessageId){
        List<ChatMessage> messages = chatMessages;
        if(lastChatMessageId!=null){

            int index = IntStream.range(0,messages.size())
                    .filter(i -> chatMessages.get(i).getId()==lastChatMessageId)
                    .findFirst()
                    .orElse(-1);
            if (index != -1) {
                messages = messages.subList(index + 1, messages.size());
            }
        }
        model.addAttribute("messages",messages);
        return "domain/chat/chatRoom/room";
    }//리포지토리에서 가져오기.
}
