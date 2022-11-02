package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.message.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByMemberListContains(Member member);

}
