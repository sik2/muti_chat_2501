package com.ll.mutiChat.domain.chat.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    //웹소켓의 핸드셰이크를 위한 엔드포인트
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("ws")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //서버가 클라이언트에게 주는 메시지의 엔드포인트 (클라이언트는 구독자고 서버는 퍼블리셔.)
        // sub으로 시작되는 request을 구독(관찰)한 모든 사용자들에게 메시지를 broadcast하게 된다.
      registry.enableSimpleBroker("/sub");
      //클라이언트가 서버에게 주는 메시지의 엔드포인트
        // pub로 시작되는 메시지는 message-handling methods로 라우팅된다는 의미다.
      registry.setApplicationDestinationPrefixes("/pub");
    }
}
