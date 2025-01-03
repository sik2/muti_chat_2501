package com.ll.mutiChat.domain.chat.ChatMessage.controller;


import com.ll.mutiChat.domain.chat.Ut;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SSEEmitters {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter add(SseEmitter emitter) {
        this.emitters.add(emitter);
        // 클라이언트와의 연결이 완료되면 컬렉션에서 제거하는 콜백. 근데 왜 제거할까?
        emitter.onCompletion(() -> {
            this.emitters.remove(emitter);
        });

        emitter.onTimeout(() -> {emitter.complete();});
        return emitter;
    }

    /*sse에서 회신하기 위한 깡통을 매개변수로 담아 오버로딩 메서드를 실행하는 코드*/
    public void changedEvent(String eventName) {
        changedEvent(eventName, Ut.mapOf());
      }

    /*SSE 프로토콜은 클라이언트에게 값을 스트리밍 해주는 기능이기에,
      회신해주려는 값이 "필수"이다. 오히려 NAME은 세팅하지않으면
      기본값인 message로 자동 회신된다고 하는데, 값은 필수이기에, 주석처리한 부분이
      동작하지 않았던것이다.
    * */
    public void changedEvent(String eventName,Map<String, Object> data) {
        this.emitters.forEach(emitter -> {
            try {
                //emitter.send(SseEmitter.event().name(eventName));
                emitter.send(
                        SseEmitter.event()
                                .name(eventName)    // 이벤트 이름 설정
                                .data(data)         // 전송할 데이터 설정
                );
            } catch (Exception e) {
                this.emitters.remove(emitter);
            }
        });
    }

}
