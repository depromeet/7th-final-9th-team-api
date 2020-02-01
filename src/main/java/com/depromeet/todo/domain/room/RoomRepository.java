package com.depromeet.todo.domain.room;

import com.depromeet.todo.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomIdAndOwner(Long roomId, Member owner);

    boolean existsByOwnerAndType(Member owner, RoomType type);

    Page<Room> findByOwner(Member owner, Pageable pageable);
}
