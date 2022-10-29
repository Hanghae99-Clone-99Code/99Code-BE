package com.hanghae.code99.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class NicknameCheckDto {
    @NotNull(message = "닉네임은 공백이 될 수 없습니다.")
    @Pattern(regexp = "^[0-9A-Za-z가-힣]{4,20}$", message = "닉네임은 한글,영문,숫자 4~20자리여야 합니다.")
    private String nickname;
}
