package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.Member;
import com.hanghae.code99.domain.message.Room;
import com.hanghae.code99.domain.message.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findAllByMember(Member member);
    List<RoomMember> findAllByRoom(Optional<Room> room);

    @Override
    void deleteById(Long aLong);
}
