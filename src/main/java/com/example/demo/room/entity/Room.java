package com.example.demo.room.entity;

import com.example.demo.chat.entity.Chat;
import com.example.demo.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)  // Enum을 String 형태로 저장
    @Column(nullable = false)
    private RoomType roomType;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    public Room(String name, RoomType roomType) {
        this.name = name;
        this.roomType = roomType;
    }
}
