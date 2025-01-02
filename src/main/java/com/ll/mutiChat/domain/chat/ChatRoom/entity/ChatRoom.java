package com.ll.mutiChat.domain.chat.ChatRoom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.global.baseEntity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Setter
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
public class ChatRoom extends BaseEntity {
    private String name;

    // ChatRoom 과 ChatMessage 1:N 관계
    // ChatRoom 이 저장, 업데이트, 삭제되면 해당 채팅방에 메시지도 동일
    // ChatMessage 가 ChatRoom 에 속하지 않으면 DB 에서도 자동 삭제
    // 메시지 최신순
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @OrderBy("id DESC")
    @JsonIgnore
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public ChatMessage writeMessage(String writerName, String content) {
        ChatMessage chatMessage = ChatMessage
                .builder()
                .chatRoom(this)
                .writerName(writerName)
                .content(content)
                .build();

        chatMessages.add(chatMessage);

        return chatMessage;
    }
}
