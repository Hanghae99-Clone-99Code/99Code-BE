package com.hanghae.code99.domain.message;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.code99.controller.request.RoomRequestDto;
import com.hanghae.code99.domain.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Room extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column
    private String roomName;

    @Column
    private String imageUrl;

    @Column
    private String description;

    @OneToOne
    private Files files;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<RoomMember> memberList;

    public boolean inMember(RoomMember roomMember) {
        return this.memberList.contains(roomMember);
    }
    public void update(RoomRequestDto roomRequestDto) {
        this.roomName = roomRequestDto.getRoomName();
        this.description = roomRequestDto.getDescription();
    }
}

