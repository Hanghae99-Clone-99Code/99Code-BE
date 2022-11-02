package com.hanghae.code99.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
  private Long id;
  private String email;
  private String nickname;
  private String profilePic;
  private String introduce;
  private String hashtag;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;



//  public MemberResponseDto(Member member){
//    this.id = member.getMemberId();
//    this.email = member.getEmail();
//    this.nickname = member.getNickname();
//    this.profilePic = member.getProfilePic();
//    this.introduce = member.getIntroduce();
//    this.hashtag = member.getHashtag();
//    this.createdAt = member.getCreatedAt();
//    this.modifiedAt = member.getModifiedAt();
//  }
}
