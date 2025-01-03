package com.ll.multiChat.domain.chat.chatRoom.controller;

import com.ll.multiChat.domain.chat.chatMessage.DTO.GetMessageResponse;
import com.ll.multiChat.domain.chat.chatMessage.DTO.WriteMessageRequest;
import com.ll.multiChat.domain.chat.chatMessage.DTO.WriteMessageResponse;
import com.ll.multiChat.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.multiChat.domain.chat.chatMessage.service.ChatMessageService;
import com.ll.multiChat.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.multiChat.domain.chat.chatRoom.service.ChatRoomService;
import com.ll.multiChat.global.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {

  private final ChatRoomService chatRoomService;
  private final ChatMessageService chatMessageService;


  @GetMapping("/list")
  public ModelAndView list() {
    ModelAndView mv = new ModelAndView();
    mv.addObject("chatRooms", chatRoomService.list());
    mv.setViewName("domain/chat/chatRoom/list");
    return mv;
  }

  @GetMapping("/make")
  public String makeRoom() {
    return "domain/chat/chatRoom/make";
  }

  @PostMapping("/make")
  public String makeRoom(String name) {
    chatRoomService.addChatRoom(name);
    return "redirect:/chat/room/list";
  }

  @GetMapping("/{roomId}")
  public ModelAndView showRoom(@PathVariable long roomId, @RequestParam(defaultValue = "NoName") String writerName) {
    ModelAndView mv = new ModelAndView();
    ChatRoom room = chatRoomService.findById(roomId).get();
    mv.addObject("room", room);
    mv.setViewName("domain/chat/chatRoom/room");
    return mv;
  }

  @PostMapping(value = "/{roomId}/write")
  @ResponseBody
  public RsData<WriteMessageResponse> write(@PathVariable long roomId, @RequestBody
  WriteMessageRequest writeMessageRequest) {
    ChatMessage chatMessage = chatRoomService.write(roomId, writeMessageRequest.writerName(), writeMessageRequest.content());
    return RsData.of("200", "메세지가 작성되었습니다.", new WriteMessageResponse(chatMessage.getId()));
  }


  @GetMapping("/{roomId}/messagesAfter/{afterId}")
  @ResponseBody
  public RsData<GetMessageResponse> getMessagesAfter(@PathVariable long roomId, @PathVariable long afterId) {
    List<ChatMessage> messages = chatMessageService.findAllAfterMessages(roomId, afterId);
    return RsData.of("200", "메세지 가져오기 성공", new GetMessageResponse(messages));
  }

}
