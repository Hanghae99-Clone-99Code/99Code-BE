package com.hanghae.code99.service;

import com.hanghae.code99.controller.request.LoginRequestDto;
import com.hanghae.code99.controller.request.SignUpRequestDto;
import com.hanghae.code99.controller.response.MemberResponseDto;
import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.domain.DefaultImages;
import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.jwttoken.JwtTokenDto;
import com.hanghae.code99.jwt.TokenProvider;
import com.hanghae.code99.repositrory.MemberRepository;
import com.hanghae.code99.repositrory.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;


    // 회원가입
    public ResponseDto<?> signup(SignUpRequestDto requestDto) {
        // 중복체크
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
        if (memberRepository.existsByNickname(requestDto.getNickname())) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }
        // 비밀번호 확인하기(password, passwordCheck)
        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 패스워드 인코딩(비밀번호 암호화 해서 저장하기)
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        // 기본 이미지(랜덤)
        String defaultProfilePic = DefaultImages.getRandomMemberPic();
        // 맴버 객체 생성
        Member member = Member.of(requestDto, encodedPassword, defaultProfilePic);
        // 저장
        memberRepository.save(member);
        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getMemberId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .createdAt(member.getCreatedAt())
                        .build());
    }

    // 로그인
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        // 이메일 검증
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 잘못 입력하셨습니다.");
        }

        JwtTokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getMemberId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .createdAt(member.getCreatedAt())
                        .build()
        );
    }

    public void tokenToHeaders(JwtTokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
    }

    // 로그아웃
    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }

        return tokenProvider.deleteRefreshToken(member);
    }

//    @Transactional(readOnly = true)
//    public ResponseDto<?> checkDupNickname(String nickname) {
//        if (null != isPresentNickname(nickname)) {
//            return ResponseDto.fail("DUPLICATED_NICKNAME", "중복된 닉네임입니다.");
//        }
//        return ResponseDto.success("사용가능한 닉네임입니다.");
//    }


//    @Transactional(readOnly = true)
//    public ResponseDto<?> checkDupEmail(String email) {
//        if (null != isPresentEmail(email)) {
//            return ResponseDto.fail("DUPLICATED_EMAIL", "중복된 이메일입니다.");
//        }
//        return ResponseDto.success("사용가능한 이메일입니다.");
//    }

    @Transactional(readOnly = true)
    public ResponseDto<?> checkDupEmail(SignUpRequestDto requestDto){
        if (null != isPresentEmail(requestDto.getEmail())) {
            return ResponseDto.fail("DUPLICATED_EMAIL", "중복된 이메일입니다.");
        }
        return ResponseDto.success("사용가능한 이메일입니다.");
    }
    @Transactional(readOnly = true)
    public ResponseDto<?> checkDupNickname(SignUpRequestDto requestDto){
        if (null != isPresentNickname(requestDto.getNickname())) {
            return ResponseDto.fail("DUPLICATED_NICKNAME", "중복된 닉네임입니다.");
        }
        return ResponseDto.success("사용가능한 닉네임입니다.");
    }

    @Transactional(readOnly = true)
    public Member isPresentEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    @Transactional(readOnly = true)
    public Member isPresentNickname(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }
}




