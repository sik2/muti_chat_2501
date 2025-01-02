package com.ll.mutiChat.domain.chat.ChatMessage.controller;

import com.ll.mutiChat.domain.chat.ChatMessage.ChatMessageRequestDto;
import com.ll.mutiChat.domain.chat.ChatMessage.ChatMessageResponseDto;
import com.ll.mutiChat.domain.chat.ChatMessage.MessageListDto;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/{roomId}/messagesAfter/{afterId}")
    @ResponseBody
    public RsData<MessageListDto> getAfterMessage(@PathVariable("roomId") Long roomId, @PathVariable("afterId") Long afterId) {
        ChatRoom chatRoom = chatRoomService.findChatRoom(roomId);
        List<ChatMessage> messages = chatRoom.getChatMessages();

        messages.forEach(message->{
            System.out.println("msg id:"+message.getId()+", msg writer"+message.getWriterName()+", msg content:"+message.getContent());
        });

        List<ChatMessageResponseDto> list=
                messages.stream()
                        .map(message->new ChatMessageResponseDto(message.getId(), message.getWriterName(), message.getContent()))
                        .collect(Collectors.toList());

        int idx=
                IntStream.range(0, messages.size())
                        .filter(i->messages.get(i).getId().equals(afterId))
                        .findFirst()
                        .orElse(-1);

        if(idx!=-1){
            list=list.subList(idx+1, list.size());
        }


        return RsData.of("200","S-read",new MessageListDto(list,list.size()));
    }

    @PostMapping("/{roomId}/write")
    @ResponseBody
    public RsData<ChatMessageResponseDto> writeMessage(@PathVariable("roomId") Long roomId, @RequestBody ChatMessageRequestDto dto) { //추후 RequestBody로 받아옴
        Long id=chatMessageService.writeMessage(roomId, dto.getWriterName(),dto.getContent());

        ChatMessageResponseDto message=new ChatMessageResponseDto(id, dto.getWriterName(), dto.getContent());
        String link="/sub/chat/room/"+roomId+"/write";
        messagingTemplate.convertAndSend(link, message);

        return RsData.of("200","S-write",message);
    }
}
