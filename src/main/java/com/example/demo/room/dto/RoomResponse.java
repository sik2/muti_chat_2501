package com.example.demo.room.dto;

import com.example.demo.room.entity.Room;
import com.example.demo.room.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomResponse {
    private Long id;
    private String name;
    private int chatCount;
    private LocalDateTime createDate;
    private RoomType roomType;

    // 엔티티 -> DTO 변환 메소드
    public RoomResponse(Room room) {
        this.id = room.getId();
        this.name =room.getName();
        this.chatCount = room.getChats().size();
        this.createDate = room.getCreateDate();
        this.roomType = room.getRoomType();
    }
}
