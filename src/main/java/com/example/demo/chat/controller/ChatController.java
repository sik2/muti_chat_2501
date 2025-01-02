package com.example.demo.chat.controller;

import com.example.demo.room.entity.RoomType;
import com.example.demo.sse.SseEmitters;  // SseEmitters 추가
import com.example.demo.chat.ChatRepository;
import com.example.demo.chat.dto.ChatListResponse;
import com.example.demo.chat.dto.ChatResponse;
import com.example.demo.chat.dto.CreateChatRequest;
import com.example.demo.chat.entity.Chat;
import com.example.demo.global.dto.RsData;
import com.example.demo.room.RoomRepository;
import com.example.demo.room.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v0/rooms/{roomId}/chats")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;
    private final SseEmitters sseEmitters;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    @ResponseBody
    public RsData<ChatListResponse> getChatList(
            @PathVariable("roomId") Long roomId,
            @RequestParam(value = "chatId", required = false) Long chatId
    ) {
        // parameter 로 chatId 없으면 roomId 전체 Chat list 가져오기
        // parameter 로 chatId 있으면 해당 chatId 이후의 Chat list 가져오기
        List<Chat> chats;
        if (chatId == null) {
            chats = chatRepository.findByRoomId(roomId);
        } else {
            chats = chatRepository.findAllByRoomIdAndIdGreaterThan(roomId, chatId);
        }

        return RsData.of("200", "채팅 리스트 가져오기 성공", new ChatListResponse(chats));
    }

    @PostMapping
    @ResponseBody
    public RsData<ChatResponse> writeChat(@PathVariable("roomId") Long roomId, @RequestBody CreateChatRequest createChatRequest) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 방이 없습니다: " + roomId));

        Chat cm = new Chat(createChatRequest.getAuthorName(), createChatRequest.getContent(), room);
        chatRepository.save(cm);

        RoomType roomType = room.getRoomType();
        if(roomType == RoomType.SSE){
            // SSE 이벤트 발생
            log.info("SSE 이벤트 발생 - roomId: {}, authorName: {}, content: {}", roomId, cm.getAuthorName(), cm.getContent());
            sseEmitters.noti("chat__messageAdded");
        }
        else if(roomType == RoomType.WEBSOCKET){
            messagingTemplate.convertAndSend("/topic/chat/writeMessage", new ChatResponse(cm));
        }

        return RsData.of("200", "채팅 작성 성공", new ChatResponse(cm));
    }
}
