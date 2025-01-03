package com.ll.mutiChat.domain.chat.ChatMessage.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.dto.ChatMessageRequestDto;
import com.ll.mutiChat.domain.chat.ChatMessage.dto.ChatMessageResponseDto;
import com.ll.mutiChat.domain.chat.ChatMessage.dto.MessageListDto;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

//    @GetMapping("/{roomId}/messagesAfter/{afterId}")
//    @ResponseBody
//    TODO: 애초에 room에 접속할 때 room에 존재하는 모든 메시지를 가져오고, SimpMessagingTemplate이 프론트로 직접 채팅 메시지를 쏴주므로 백엔드에서 직접 메시지를 쏠 필요가 없다.
//    SSE나 폴링 방식이었다면 대신 데이터를 쏴줄 친구가 필요하므로 이 메서드가 필요할 것이다.
//    public RsData<MessageListDto> getAfterMessage(@PathVariable("roomId") Long roomId, @PathVariable("afterId") Long afterId) {
//        ChatRoom chatRoom = chatRoomService.findChatRoom(roomId);
//        List<ChatMessage> messages = chatRoom.getChatMessages();
//
//        //ChatMessage를 그대로 리턴하면 JSON 매핑 시 ChatRoom에 대한 순환 참조가 일어날 수 있으므로 ChatMessageResponseDto로 변환
//        List<ChatMessageResponseDto> list=
//                messages.stream()
//                        .map(message->new ChatMessageResponseDto(message.getId(), message.getWriterName(), message.getContent()))
//                        .collect(Collectors.toList());
//
//        //chatRoom과 관련된 채팅의 ID가 순서대로가 아닐 수도 있으므로, afterId와 동일한 ID를 가진 채팅의 인덱스를 먼저 찾는다.
//        int idx=
//                IntStream.range(0, messages.size())
//                        .filter(i->messages.get(i).getId().equals(afterId))
//                        .findFirst()
//                        .orElse(-1);
//
//        //찾았다면 인덱스+1부터 끝까지의 채팅DTO를 JSON으로 변환한다.
//        if(idx!=-1){
//            list=list.subList(idx+1, list.size());
//        }
//
//
//        return RsData.of("200","S-read",new MessageListDto(list,list.size()));
//    }

    @PostMapping("/{roomId}/write")
    @ResponseBody
    public RsData<ChatMessageResponseDto> writeMessage(@PathVariable("roomId") Long roomId, @RequestBody ChatMessageRequestDto dto) { //추후 RequestBody로 받아옴
        Long id=chatMessageService.writeMessage(roomId, dto.getWriterName(),dto.getContent());

        ChatMessageResponseDto message=new ChatMessageResponseDto(id, dto.getWriterName(), dto.getContent());

        //클라이언트는 /sub/chat/room/{roomId}/write를 구독하고 있으므로, 해당 링크로 메시지를 전송한다.
        String link="/sub/chat/room/"+roomId+"/write";
        //converAndSend이므로, 아마 브로커를 거친 후 메시지를 전달하는 동작일 것이다.
        messagingTemplate.convertAndSend(link, message);

        return RsData.of("200","S-write",message);
    }
}
