package com.ll.mutiChat.domain.chat.chatMessage.controller;

import com.ll.mutiChat.domain.chat.chatMessage.service.ChatMessageService;
import com.ll.mutiChat.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    // 채팅 메시지 작성
    @PostMapping("/{roomId}/write")
    @ResponseBody
    public RsData writeMessage(@PathVariable Long roomId,
                               @RequestBody HashMap message) {

        System.out.println("roomId = " + roomId);
        boolean result = chatMessageService.writeMessage(roomId, message);  // 메시지 작성 성공여부 확인

        if (result) {
            return RsData.of("S-1", "채팅 메시지 작성 완료");
        } else {
            return RsData.of("F-1", "채팅 메시지 작성 중 오류 발생");
        }
    }

}
