package com.ll.mutiChat.domain.chat.ChatMessage.service;

import com.ll.mutiChat.domain.chat.ChatMessage.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
}
