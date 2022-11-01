package com.hanghae.code99.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
    @NotNull(message = "닉네임은 공백이 될 수 없습니다.")
    @Pattern(regexp = "^[0-9A-Za-z가-힣]{2,20}$", message = "닉네임은 한글,영문,숫자 2~20자리여야 합니다.")
    private String nickname;
//    private String profilePic;
    private String introduce;
}
