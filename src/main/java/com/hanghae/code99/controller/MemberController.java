package com.hanghae.code99.controller;

import com.hanghae.code99.controller.request.EmailCheckDto;
import com.hanghae.code99.controller.request.LoginRequestDto;
import com.hanghae.code99.controller.request.NicknameCheckDto;
import com.hanghae.code99.controller.request.SignUpRequestDto;
import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.jwttoken.JwtTokenDto;
import com.hanghae.code99.jwt.TokenProvider;
import com.hanghae.code99.jwt.userdetails.UserDetailsImpl;
import com.hanghae.code99.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;


    // 회원가입
    @PostMapping("/api/members/signup")
    public ResponseDto<?> signup(@RequestBody SignUpRequestDto requestDto) {
        return memberService.signup(requestDto);
    }

    // 로그인
    @PostMapping("/api/members/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto,
                                HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    //토큰재발급
    @GetMapping("/api/auth/members/reissue")
    public ResponseDto<?> reissue(
            @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response
    ) {
        Member member = userDetails.getMember();
        JwtTokenDto tokenDto = tokenProvider.generateTokenDto(member);
        memberService.tokenToHeaders(tokenDto, response);

        return ResponseDto.success("재발급 완료");
    }

    // 로그아웃
    @PostMapping("/api/auth/members/logout")
    public ResponseDto<?> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }

//    //이메일 중복확인
//    @GetMapping("/api/members/check/{email}")
//    public ResponseDto<?> checkDupEmail(@PathVariable String email){
//        return memberService.checkDupEmail(email);
//    }

    @PostMapping("/api/members/check/email")
    public ResponseDto<?> emailCheck(@RequestBody EmailCheckDto requestDto){
        return memberService.checkDupEmail(requestDto);
    }
//    //닉네임 중복확인
//    @GetMapping("/api/members/check/{nickname}")
//    public ResponseDto<?> checkDupNickname(@PathVariable String nickname){
//        return memberService.checkDupNickname(nickname);
//    }
    @PostMapping("/api/members/check/nick")
    public ResponseDto<?> nickCheck(@RequestBody NicknameCheckDto requestDto){
        return memberService.checkDupNickname(requestDto);
    }
}
