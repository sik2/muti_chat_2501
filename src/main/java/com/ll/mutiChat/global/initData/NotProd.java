package com.ll.mutiChat.global.initData;

import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

//application-local, application-dev 처럼 배포할 서버환경별로
//설정을 나눌 수 있다.
@Configuration
@Profile("!prod")
public class NotProd {

    @Bean
    public ApplicationRunner initNotProd(ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
        return args -> {

            ChatRoom chatRoom1 = chatRoomService.make("공부");
            ChatRoom chatRoom2 = chatRoomService.make("학습");
            ChatRoom chatRoom3 = chatRoomService.make("식사");

            IntStream.rangeClosed(1, 5).forEach(i -> {
                //공부방에 5개의 메시지를 넣는다.
                chatMessageService.makeBasicMessageList(chatRoom1,"1번방사용자", "1번방내용");
                chatMessageService.makeBasicMessageList(chatRoom2,"2번방사용자", "2번방내용");
            });
        };
    }
}
