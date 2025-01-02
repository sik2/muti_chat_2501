package com.example.demo.chat.dto;

import com.example.demo.chat.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatResponse {
    private long id;
    private String authorName;
    private String content;
    private Long roomId;  // Room 엔티티 대신 roomId만 반환

    public ChatResponse(Chat chat) {
        this.id = chat.getId();
        this.authorName = chat.getAuthorName();
        this.content = chat.getContent();
        this.roomId = chat.getRoom().getId();  // Room 전체 대신 ID만 포함
    }
}
