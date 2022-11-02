package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.message.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findAllByMember(Member member);

    @Override
    void deleteById(Long aLong);
}
