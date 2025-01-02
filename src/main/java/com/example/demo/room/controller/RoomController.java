package com.example.demo.room.controller;

import com.example.demo.global.dto.RsData;
import com.example.demo.room.RoomRepository;
import com.example.demo.room.dto.RoomListResponse;
import com.example.demo.room.dto.RoomRequest;
import com.example.demo.room.dto.RoomResponse;
import com.example.demo.room.entity.Room;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v0/rooms")
public class RoomController {
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // 방 목록 조회 (lastRoomId가 있을 때는 이후의 방만, 없을 때는 전체 조회)
    @GetMapping
    @ResponseBody
    public RsData<RoomListResponse> getAllRooms(@RequestParam(value = "lastRoomId", required = false) Long lastRoomId) {
        List<Room> rooms;

        if (lastRoomId == null) {
            // lastRoomId가 없는 경우 전체 조회
            rooms = roomRepository.findAll();
        } else {
            // lastRoomId 이후의 방만 조회
            rooms = roomRepository.findRoomsAfterId(lastRoomId);
        }

        return RsData.of("200", "방 리스트 가져오기 성공", new RoomListResponse(rooms));
    }

    @GetMapping("/{id}")
    public RsData<RoomResponse> getRoomById(@PathVariable("id") Long id) {
        Room findedRoom = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 방이 없습니다: " + id));
        return RsData.of("200", "방 조회 성공", new RoomResponse(findedRoom));
    }

    // 새로운 방 생성
    @PostMapping
    @ResponseBody
    public RsData<RoomResponse> createRoom(@RequestBody RoomRequest request) {
        Room newRoom = new Room(request.getName(), request.getRoomTypeEnum());
        roomRepository.save(newRoom);

        return RsData.of("200", "방 생성 성공", new RoomResponse(newRoom));
    }

}
