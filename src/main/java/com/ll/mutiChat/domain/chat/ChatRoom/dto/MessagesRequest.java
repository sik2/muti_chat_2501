package com.ll.mutiChat.domain.chat.ChatRoom.dto;

public record MessagesRequest(Long fromId,Long roomId) {
    //null값을 넣을 수 있음. long 같은 경우는 기본형이어서 null 불가.
}
