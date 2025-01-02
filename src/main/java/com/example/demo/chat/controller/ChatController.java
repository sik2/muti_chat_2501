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
            @RequestParam(value = "chatId", required = false) Long chatId,
            @RequestParam(value = "lastChatId", required = false) Long lastChatId
    ) {

        List<Chat> chats = null;
        if (chatId == null && lastChatId == null) {
            // 아무런 parameter 값이 없을시, roomId에 해당하는 모든 값들가져오기
            chats = chatRepository.findByRoomId(roomId);
        }
        else if(chatId != null) {
            // RoomId 값이 있고, chatId 값이 있을시, roomId와 chatId에 해당하는 값 가져오기, 하나만 가져오면 된다.
            Chat tmpChat = chatRepository.findByRoomIdAndId(roomId, chatId);
            if (tmpChat != null){
                chats.add(tmpChat);
            }
        }
        else if(lastChatId != null){
            // RoomId 값이 있고, lastChatId 값이 있을시, roomId와 lastChatId 이후의 값들 가져오기
            chats = chatRepository.findAllByRoomIdAndIdGreaterThan(roomId, lastChatId);
        }
        else {
            throw new IllegalArgumentException("잘못된 파라미터 입니다.");
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
