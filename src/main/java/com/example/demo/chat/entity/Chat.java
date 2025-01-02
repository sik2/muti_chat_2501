package com.example.demo.chat.entity;

import com.example.demo.global.BaseEntity;
import com.example.demo.room.entity.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat extends BaseEntity {

    private String authorName;
    private String content;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
