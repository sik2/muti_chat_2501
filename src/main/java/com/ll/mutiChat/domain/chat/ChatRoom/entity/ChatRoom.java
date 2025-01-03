package com.ll.mutiChat.domain.chat.ChatRoom.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ll.mutiChat.domain.chat.ChatMessage.entity.ChatMessage;
import com.ll.mutiChat.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@JsonIgnoreProperties({"chatMessages"})
public class ChatRoom extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages;
}
