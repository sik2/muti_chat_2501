package com.example.demo.global;

import com.example.demo.room.RoomRepository;
import com.example.demo.room.entity.Room;
import com.example.demo.room.entity.RoomType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(RoomRepository roomRepository) {
        return args -> {
            // 0번 Room이 존재하지 않으면 생성
            Room room1 = new Room("폴링 방", RoomType.POLLING);
            roomRepository.save(room1);

            // 1번 Room이 존재하지 않으면 생성
            Room room2 = new Room("SSE 방", RoomType.SSE);
            roomRepository.save(room2);

            Room room3 = new Room("웹소켓 방", RoomType.WEBSOCKET);
            roomRepository.save(room3);

            Room room4 = new Room("AI와 소통하는방", RoomType.AI);
            roomRepository.save(room4);
        };
    }
}