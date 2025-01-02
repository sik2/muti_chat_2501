package com.ll.mutiChat.domain.chat.ChatRoom.service;

import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;

	public ChatRoom saveRoom(String name) {
		return chatRoomRepository.save(ChatRoom.builder()
			.name(name)
			.build());
	}
}
