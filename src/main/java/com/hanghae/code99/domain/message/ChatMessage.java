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

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "room_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom roomid;

    @Column
    private String message;
}