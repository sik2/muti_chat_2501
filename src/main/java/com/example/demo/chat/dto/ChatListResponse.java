package com.example.demo.chat.dto;

import com.example.demo.chat.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatListResponse {
    private List<ChatResponse> chats;  // Chat 대신 ChatResponse 사용
    private int totalCount;

    // Chat 엔티티 리스트를 ChatResponse 리스트로 변환하는 생성자
    public ChatListResponse(List<Chat> chatList) {
        this.chats = chatList.stream()
                .map(ChatResponse::new)  // Chat -> ChatResponse로 변환
                .collect(Collectors.toList());
        this.totalCount = chatList.size();
    }
}
