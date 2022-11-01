package com.hanghae.code99.domain.message;


import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.Timestamped;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class ChatRoom extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column
    private String roomName;

    @Column
    private String imageUrl;

    @Column
    private String description;

    @OneToMany(mappedBy = "memberId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members;

    public ChatRoom(String roomName, Member member) {
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);
        this.roomName = roomName;
        this.members = memberList;
    }
}

