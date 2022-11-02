package com.hanghae.code99.domain.message;

import com.hanghae.code99.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "roomId", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @JoinColumn(name = "memberId", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
