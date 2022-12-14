package com.hanghae.code99.service;

import com.hanghae.code99.controller.request.*;
import com.hanghae.code99.controller.response.MemberResponseDto;
import com.hanghae.code99.controller.response.ResponseDto;
import com.hanghae.code99.domain.DefaultImages;
import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.jwttoken.JwtTokenDto;
import com.hanghae.code99.jwt.TokenProvider;
import com.hanghae.code99.repositrory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;


    // 회원가입
    public ResponseDto<?> signup(SignUpRequestDto requestDto) {
        // 중복체크
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
        // 비밀번호 확인하기(password, passwordCheck)
        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 패스워드 인코딩(비밀번호 암호화 해서 저장하기)
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        // 기본 이미지(랜덤)
        String defaultProfilePic = DefaultImages.getRandomMemberPic();
        // 고유 해시태그 번호(식별번호) 생성(랜덤)
        Random random = new Random();
        String hashtag = "#" + (random.nextInt(999999));
        // 해시태그 중복검사(while 조건문이 참인 동안 아래 실행)
        while (null != isPresentHashtag(hashtag)) {
            hashtag = "#" + (random.nextInt(999999));
        }
        // 맴버 객체 생성
        Member member = Member.of(requestDto, encodedPassword, defaultProfilePic, hashtag);
        // 저장
        memberRepository.save(member);
        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getMemberId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .profilePic(member.getProfilePic())
                        .introduce(member.getIntroduce())
                        .hashtag(member.getHashtag())
                        .status(member.isStatus())
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

        // 접속 중
        member.setStatus(true);
        // DB에 status 업데이트
        memberRepository.save(member);



        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getMemberId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .profilePic(member.getProfilePic())
                        .introduce(member.getIntroduce())
                        .hashtag(member.getHashtag())
                        .status(member.isStatus())
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
        //접속 해제
        member.setStatus(false);
        // DB에 status 업데이트
        memberRepository.save(member);

        return tokenProvider.deleteRefreshToken(member);
    }


    // 내 프로필 조회
    public ResponseDto<?> myProfile(Member member) {

        return ResponseDto.success(MemberResponseDto.builder()
                .id(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profilePic(member.getProfilePic())
                .introduce(member.getIntroduce())
                .hashtag(member.getHashtag())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build());
    }



        // 내 프로필 수정
    public ResponseDto<?> editProfile(Member member, ProfileRequestDto profileRequestDto) {

        // 회원 정보 업데이트
        member.editProfile(profileRequestDto);
        // 저장
        memberRepository.save(member);

        return ResponseDto.success(MemberResponseDto.builder()
                .id(member.getMemberId())
                .nickname(member.getNickname())
                .profilePic(member.getProfilePic())
                .introduce(member.getIntroduce())
                .hashtag(member.getHashtag())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build());
    }

    //개별 회원정보 조회
    public ResponseDto<?> viewProfile(Long member_id) {
        Optional<Member> optionalMember = memberRepository.findById(member_id);

        Member member = optionalMember.orElse(null);

        if (null != member){
            return ResponseDto.success(MemberResponseDto.builder()
                    .id(member.getMemberId())
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .profilePic(member.getProfilePic())
                    .introduce(member.getIntroduce())
                    .hashtag(member.getHashtag())
                    .createdAt(member.getCreatedAt())
                    .build());
        }

        return ResponseDto.fail("NOT_FOUND","해당 아이디가 없습니다");
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> checkDupEmail(EmailCheckDto requestDto){
        if (null != isPresentEmail(requestDto.getEmail())) {
            return ResponseDto.fail("DUPLICATED_EMAIL", "중복된 이메일입니다.");
        }
        return ResponseDto.success("사용가능한 이메일입니다.");
    }


    @Transactional(readOnly = true)
    public Member isPresentEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    @Transactional(readOnly = true)
    public Member isPresentHashtag(String hashtag) {
        Optional<Member> optionalMember = memberRepository.findByHashtag(hashtag);
        return optionalMember.orElse(null);
    }

}




