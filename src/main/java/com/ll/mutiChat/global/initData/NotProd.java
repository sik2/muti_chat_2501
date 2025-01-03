package com.ll.mutiChat.global.initData;

import com.ll.mutiChat.domain.chat.ChatMessage.service.ChatMessageService;
import com.ll.mutiChat.domain.chat.ChatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.ChatRoom.service.ChatRoomService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Random;
import java.util.stream.IntStream;

@Configuration
@Profile("!prod")
public class NotProd {

    @Bean
    public ApplicationRunner initNotProd(ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
        return args -> {
            ChatRoom room1=chatRoomService.createChatRoom("공부");
            ChatRoom room2=chatRoomService.createChatRoom("식사");
            ChatRoom room3=chatRoomService.createChatRoom("잡담");
            ChatRoom[] roomIds={room1,room2,room3};

            Random random=new Random();
            random.setSeed(System.currentTimeMillis());

            IntStream.rangeClosed(1,100)
                    .forEach(
                            num->{
                                Long roomId=roomIds[random.nextInt(3)].getId();
                                chatMessageService.writeMessage(roomId,"writer"+num,"content"+num);
                            }
                    );
        };
    }
}
