package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);
}
