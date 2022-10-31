package com.hanghae.code99.domain.message;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column
    private String roomName;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
    }
}

