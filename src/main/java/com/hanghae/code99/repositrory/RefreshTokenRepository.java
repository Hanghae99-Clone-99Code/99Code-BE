package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.jwttoken.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);

    Optional<RefreshToken> findByTokenValue(String refreshToken);
}
