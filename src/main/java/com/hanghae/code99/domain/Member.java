package com.hanghae.code99.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.code99.controller.request.ProfileRequestDto;
import com.hanghae.code99.controller.request.SignUpRequestDto;
import com.hanghae.code99.domain.message.RoomMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//무분별한 객체 생성을 막는다
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String profilePic;

    private String introduce;

    private String hashtag;
    private boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomMember> roomList;


    private Member(String email, String password, String nickname, String profilePic, String hashtag) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profilePic = profilePic;
        this.hashtag = hashtag;
        this.introduce = "";
    }

    public static Member of(SignUpRequestDto requestDto, String encodedPassword, String profilePic, String hashtag) {
        return new Member(requestDto.getEmail(), encodedPassword, requestDto.getNickname(), profilePic, hashtag);
    }

    public void editProfile(ProfileRequestDto requestDto){
        this.nickname = requestDto.getNickname();
//        this.profilePic = requestDto.getProfilePic();
        this.introduce = requestDto.getIntroduce();
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
