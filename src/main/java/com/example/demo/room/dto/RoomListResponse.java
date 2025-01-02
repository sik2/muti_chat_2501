package com.example.demo.room.dto;

import com.example.demo.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomListResponse {
    private List<RoomResponse> rooms;
    private int totalCount;

    // 엔티티 리스트 -> DTO 리스트 변환 메소드
    public RoomListResponse(List<Room> roomList) {
        this.rooms = roomList.stream()
                .map(RoomResponse::new)  // Room 엔티티를 RoomResponse로 변환
                .collect(Collectors.toList());
        this.totalCount = roomList.size();
    }
}
