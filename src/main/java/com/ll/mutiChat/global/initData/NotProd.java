package com.ll.mutiChat.global.initData;

import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@Configuration
@Profile("!prod")
public class NotProd {

    @Bean
    public ApplicationRunner initNotProd(ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
        return args -> {
            ChatRoom chatRoom1 = chatRoomService.make("공부");
            ChatRoom chatRoom2 = chatRoomService.make("식사");
            ChatRoom chatRoom3 = chatRoomService.make("휴식");

            IntStream.rangeClosed(1, 100).forEach(num -> {
                chatMessageService.write(chatRoom1, "홍길동", "공부 메시지" + num);
            });

        };
    }
}
