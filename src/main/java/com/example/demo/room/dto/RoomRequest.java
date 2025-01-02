package com.example.demo.room.dto;

import com.example.demo.room.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomRequest {
    private String name;
    private String roomType;

    public RoomType getRoomTypeEnum() {
        return RoomType.valueOf(roomType.toUpperCase());
    }
}
