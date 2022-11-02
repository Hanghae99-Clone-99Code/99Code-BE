package com.hanghae.code99.controller;

import com.hanghae.code99.controller.request.*;
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
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;


    // 회원가입
    @PostMapping("/api/members/signup")
    public ResponseDto<?> signup(@RequestBody @Valid SignUpRequestDto requestDto) {
        return memberService.signup(requestDto);
    }

    // 로그인
    @PostMapping("/api/members/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
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

    //이메일 중복확인
    @PostMapping("/api/members/check/email")
    public ResponseDto<?> emailCheck(@RequestBody @Valid EmailCheckDto requestDto){
        return memberService.checkDupEmail(requestDto);
    }

    //닉네임 중복확인
    @PostMapping("/api/members/check/nick")
    public ResponseDto<?> nickCheck(@RequestBody @Valid NicknameCheckDto requestDto){
        return memberService.checkDupNickname(requestDto);
    }

    //내 프로필 조회
    @GetMapping("/api/auth/members/profiles")
    public ResponseDto<?> myProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Member member = userDetails.getMember();

        return  memberService.myProfile(member);
    }

    //개별 프로필 조회
    @GetMapping("/api/members/find/{memberId}")
    public ResponseDto<?> veiwProfile(@PathVariable Long memberId){
        return memberService.viewProfile(memberId);
    }
//    @GetMapping("/api/auth/members/info")
//    public ResponseDto<?> LoginInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        Member member = userDetails.getMember();
//
//        try {
//            return  memberService.LoginInfo(member);
//        } catch (Exception e) {
//            return  ResponseDto.fail("NOT_STATE_LOGIN", e.getMessage());
//        }
//    }

    //내 프로필 수정
    @PutMapping("/api/auth/members/profiles/edit")
    public ResponseDto<?> editProfile(@RequestBody @Valid ProfileRequestDto profileRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        Member member = userDetails.getMember();
        return memberService.editProfile(member, profileRequestDto);
    }

}
