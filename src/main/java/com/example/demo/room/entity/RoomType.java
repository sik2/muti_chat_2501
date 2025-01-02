package com.example.demo.room.entity;

public enum RoomType {
    POLLING(1),
    SSE(2),
    WEBSOCKET(3),
    AI(4);

    private final int code;

    RoomType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
