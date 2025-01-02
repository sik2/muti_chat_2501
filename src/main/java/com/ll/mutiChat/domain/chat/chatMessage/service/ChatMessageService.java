package com.ll.mutiChat.domain.chat.chatMessage.service;

    import com.ll.mutiChat.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.mutiChat.domain.chat.chatMessage.repository.ChatMessageRepository;
import com.ll.mutiChat.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.mutiChat.domain.chat.chatRoom.repository.ChatRoomRepository;
    import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

    import java.util.HashMap;
    import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;


    // 메시지 작성
    public boolean writeMessage(long roomId, HashMap message) {
        logger.info("메시지 작성");
        // 메시지 작성자 이름
        String writerName = (String) message.get("writerName");
        // 메시지 내용
        String content = (String) message.get("content");

        // 채팅방 찾기
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        // 새 메시지 생성
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .writerName(writerName)
                .content(content)
                .build();

        // 메시지 저장
        try {
            logger.info("메시지 작성 완료");
            chatMessageRepository.save(chatMessage);
            return true;
        } catch (Exception e) {
            logger.warning("메시지 작성 중 오류 발생");
            return false;
        }
    }
}
