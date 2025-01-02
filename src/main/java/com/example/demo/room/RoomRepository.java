package com.example.demo.room;

import com.example.demo.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // lastRoomId 이후의 방을 조회하는 쿼리
    @Query("SELECT r FROM Room r WHERE r.id > :lastRoomId ORDER BY r.id ASC")
    List<Room> findRoomsAfterId(@Param("lastRoomId") Long lastRoomId);
}
