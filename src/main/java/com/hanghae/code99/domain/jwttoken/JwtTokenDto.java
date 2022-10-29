package com.hanghae.code99.domain.jwttoken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenDto {
    private String accessToken;
    private String refreshToken;
}
