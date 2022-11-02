package com.hanghae.code99.domain.message;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.code99.domain.Member;
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

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<RoomMember> memberList;

}

