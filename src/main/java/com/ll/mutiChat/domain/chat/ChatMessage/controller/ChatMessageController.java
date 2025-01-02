package com.ll.mutiChat.domain.chat.ChatMessage.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.dto.MessagesRequest;
import com.ll.mutiChat.domain.chat.dto.MessagesResponse;
import com.ll.mutiChat.domain.chat.dto.WriteMessageRequest;
import com.ll.mutiChat.domain.chat.dto.WriteMessageResponse;
import com.ll.mutiChat.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<WriteMessageResponse> writeMessage(
        @RequestBody WriteMessageRequest writeMessageRequest) {
        ChatMessage cm = chatMessageService.writeMessage(writeMessageRequest);

        messagingTemplate.convertAndSend("/topic/chat/room/" + writeMessageRequest.getChatRoomId(), new WriteMessageResponse(cm));
        return RsData.of("200", "메세지가 작성되었습니다.", new WriteMessageResponse(cm));
    }
}
