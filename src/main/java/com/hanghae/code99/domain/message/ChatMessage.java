package com.hanghae.code99.domain.message;

import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.Timestamped;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ChatMessage extends Timestamped {

    public enum MessageType {
        ENTER, TALK
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private MessageType type;

    @JoinColumn(name = "memberId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "roomId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @Column
    private String message;
}