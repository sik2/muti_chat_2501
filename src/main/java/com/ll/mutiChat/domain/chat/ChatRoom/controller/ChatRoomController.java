package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import com.ll.mutiChat.domain.chat.ChatRoom.dto.ChatRoomDTO;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
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
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    //채팅방 목록조회
    @GetMapping("list")
    public String getRoomList(Model model) {
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        model.addAttribute("chatRooms", chatRooms);
        return "domain/chat/chatRoom/list";
    }

    //채팅방 생성화면 이동
    @GetMapping("/make")
    public String makeRoom() {
        return "domain/chat/chatRoom/make";
    }


    @PostMapping("/make")
    @ResponseBody
    public boolean makeRoom(@RequestBody ChatRoomDTO ChatRoomDTO) {
        /*ChatRoom은 ACCESS = PROTETED가 걸려있었다.
          외부에서 직접 생성자 호출하는 대신, 빌더 패턴이나 팩토리 메서드(?) 를 사용하는 목적이라는 듯 하다.
          따라서 기존의 new 방식을 주석처리하고 빌드패턴을 사용하여 작성하였다.
          ChatRoom chatRoom = new ChatRoom();
        */
        boolean result = true;
        ChatRoom chatRoom = ChatRoom.builder()
                            .name(ChatRoomDTO.getName())
                            .build();
        try {
            chatRoomService.save(chatRoom);
        } catch (Exception e) {
            result = false;
            log.error("채팅방 생성 실패", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    //채팅방 입장화면 이동
    @GetMapping("/{roomId}")
    public String showRoom(@PathVariable long roomId, @RequestParam(defaultValue = "NoName") String writerName, Model model) {
        model.addAttribute("roomId", roomId);
        model.addAttribute("writerName", writerName);
         return "domain/chat/chatRoom/room";
    }
}
