package com.ll.mutiChat.domain.chat.ChatMessage.entity;

import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Setter
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
public class ChatMessage extends BaseEntity {

    //ManyToOne은 OneToMany의 기본키값인 id에 의존한다는 의미이다.
    @ManyToOne
    private ChatRoom chatRoom;

    private String writerName;
    private String content;
}
