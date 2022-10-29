package com.hanghae.code99.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class EmailCheckDto {
    @NotNull(message = "이메일은 공백이 될 수 없습니다.")
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = "이메일 형식이 아닙니다.")
    private String email;
}
