package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.rsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // 채팅방 상세
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
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        model.addAttribute("chatRooms", chatRooms);

        return "domain/chat/chatRoom/list";
    }

    @Setter
    @Getter
    public static class WriteRequestBody {
        private String writerName;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class WriteResponseBody {
        private Long chatMessageId;
    }

    @PostMapping("/{roomId}/write")
    @ResponseBody
    public RsData<WriteResponseBody> write(
            @PathVariable final long roomId,
            @RequestBody final WriteRequestBody requestBody
    ) {
        ChatMessage chatMessage = chatRoomService.write(roomId, requestBody.getWriterName(), requestBody.getContent());

        return RsData.of("S-1", "%d번 메시지를 작성하였습니다.".formatted(chatMessage.getId()), new WriteResponseBody(chatMessage.getId()));
    }

    @Getter
    @AllArgsConstructor
    public static class GetMessagesAfterResponseBody {
        private List<ChatMessage> messages;
    }

    @GetMapping("/{roomId}/messagesAfter/{afterId}")
    @ResponseBody
    public RsData<GetMessagesAfterResponseBody> getMessagesAfter(
            @PathVariable final long roomId,
            @PathVariable final long afterId
    ) {
        List<ChatMessage> messages = chatMessageService.findByChatRoomIdAndIdAfter(roomId, afterId);

        return RsData.of("S-1", "%d개의 메시지를 가져왔습니다.".formatted(messages.size()), new GetMessagesAfterResponseBody(messages));
    }

    @GetMapping("/{roomId}/subscribe")
    public SseEmitter subscribe(@PathVariable long roomId) {
        SseEmitter emitter = new SseEmitter();

        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    private void sendNewMessageToClients(ChatMessage message) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(message, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                emitters.remove(emitter);
            }
        }
    }
}
