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

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy("id DESC")
    @JsonIgnore
    private List<ChatMessage> chatMessages;

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
