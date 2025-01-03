package com.ll.mutiChat.domain.chat.ChatMessage.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.dto.ChatMessageDTO;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat/room")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomRepository ChatRoomRepository;
    private final SSEEmitters sseEmitters;
    private final SimpMessagingTemplate messagingTemplate;

    //채팅방 입장화면 메시지 입력
    @PostMapping("/{roomId}/write")
    @ResponseBody
    public boolean createMessage(@PathVariable long roomId, @RequestBody ChatMessageDTO chatMessageDTO) {

        boolean result = true;
        /*이해를 제대로 한건지는 모르겠는데.. 일단 동작하는 걸 봐서는,
          @ManyToOne처럼 다대일 관계가 걸린 컬럼이 있는 경우엔, 해당 엔티티 전체를 읽어와야하는것같다.
          하긴 생각해보면, "내"가 "누구"에게 의존하는지 알아야 된다는걸 생각해볼때,
        상대방의 Entity 를 세팅하는것은 이해가 된다.
         */
        ChatRoom chatRoom = ChatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .writerName(chatMessageDTO.getWriterName())
                .content(chatMessageDTO.getContent())
                .build();
        try {
            chatMessageService.createMessage(chatMessage);

             /*SSE방식은
             1. 서버와 클라이언트 별도로 미리 연결.(sse연결용 Controller @GetMapping(value = "/SseConnecting")를 사용하였음
             2. 변경사항 발생시, 전체 emitter들에게 데이터 회신으로 이루어져 있는고로, 사용자가 입력=변경사항 발생시.
             emitter로 연결된 모든 이들에게 데이터를 전송해야 함으로, crud에 넣는것이 좋다.
             */
            sseEmitters.changedEvent("messageChanged");

            /*Web Socket방식.
            * 1. 서버와 클라이언트 미리 연결(sse와는 다르게, 정형화된 클래스인 WebSocketConfig.java를 사용하여 설정)
            * 2. 변경사항 발생시 동일한 Destination이 설정된 클라이언트 ws로 정보 회신
            * */
            messagingTemplate.convertAndSend("/wsConnect/write", chatMessage);
        } catch (Exception e) {
            result = false;
            log.error("채팅방 입장화면 메시지 입력 실패" + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    //특정 id이후의 채팅방 메시지 정보 가져오기
    @GetMapping("/{roomId}/messagesAfter/{afterId}")
    @ResponseBody
    public List<ChatMessage> readMessage(@PathVariable long roomId, @PathVariable long afterId, Model model) {
        List<ChatMessage> messageList= chatMessageService.findByIdGreaterThan(afterId);
        return messageList;
    }

    //SSE 연결
    @GetMapping(value = "/SseConnecting", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> SSEConnect(){

        SseEmitter emitter = new SseEmitter();
        sseEmitters.add(emitter);

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")    // 이벤트 이름을 "connect"로 지정
                    .data("connected!")); // 전송할 데이터
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);

    }
}
