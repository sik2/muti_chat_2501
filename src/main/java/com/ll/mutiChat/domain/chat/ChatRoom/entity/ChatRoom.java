package com.ll.mutiChat.domain.chat.ChatRoom.entity;

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
public class ChatRoom extends BaseEntity {
    private String name;

    //OneToMany는 다대일관계를 설정할떄 사용된다.
    //OneToMany가 설정된 대상이 일이고, ManyToOne이 설정된 대상이 다이다.
    //코드상으론 표시되지 않지만, Entity기반으로 테이블이 만들어질때,
    //ManyToOne이 설정된 Entity에는 ChatRoom의 기본키인 id를 외래키로 갖는 컬럼이 발생한다.
    //외래키를 설정하는 컬럼은 인위적으로 바꿀수는 있지만, 기본적으론 id로 설정된다.
    @OneToMany
    private List<ChatMessage> chatMessages;

    public void writeMessage(String writerName, String content) {
        ChatMessage chatMessage = ChatMessage.builder()
                .writerName(writerName)
                .content(content)
                .chatRoom(this)
                .build();
        chatMessages.add(chatMessage);
    }
}
