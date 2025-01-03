package com.ll.mutiChat.domain.chat.ChatMessage.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.dto.ChatMessageDTO;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat/room")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomRepository ChatRoomRepository;

    //채팅방 입장화면 메시지 입력
    @PostMapping("/{roomId}/write")
    @ResponseBody
    public boolean createMessage(@PathVariable long roomId, @RequestBody ChatMessageDTO chatMessageDTO) {

        boolean result = true;

        //이해를 제대로 한건지는 모르겠는데.. 일단 동작하는 걸 봐서는,
        //@ManyToOne처럼 다대일 관계가 걸린 컬럼이 있는 경우엔, 해당 엔티티 전체를 읽어와야하는것같다.
        //하긴 생각해보면, "내"가 "누구"에게 의존하는지 알아야 된다는걸 생각해볼때,
        //상대방의 Entity 를 세팅하는것은 이해가 된다.
        ChatRoom chatRoom = ChatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .writerName(chatMessageDTO.getWriterName())
                .content(chatMessageDTO.getContent())
                .build();
        try {
            chatMessageService.createMessage(chatMessage);
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
}
