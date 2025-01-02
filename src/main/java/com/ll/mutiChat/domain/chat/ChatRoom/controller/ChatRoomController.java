package com.ll.mutiChat.domain.chat.ChatRoom.controller;

import java.util.Map;

import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import com.ll.mutiChat.global.rsData.RsData;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	@GetMapping("/{roomId}")
	@ResponseBody
	public String showRoom(@PathVariable long roomId, @RequestParam(defaultValue = "NoName") String writerName) {

		return String.format("%d 번 채팅방 입니다. writer : %s", roomId, writerName);
	}

	@GetMapping("/make")
	public String makeRoom() {
		return "domain/chat/chatRoom/make";
	}

	@PostMapping("/make")
	public RsData<ChatRoom> makeRoom(@RequestBody Map<String, String> roomInfo) {
		String name = roomInfo.getOrDefault("roomInfo", "NoName");
		ChatRoom chatRoom = chatRoomService.saveRoom(name);
		return RsData.of("200", "채팅방 생성 완료", chatRoom);
	}

}
