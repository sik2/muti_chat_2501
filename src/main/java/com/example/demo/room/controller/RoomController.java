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

    @GetMapping
    @ResponseBody
    public RsData<RoomListResponse> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
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
