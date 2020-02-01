package com.depromeet.todo.domain.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomIdAndMemberId(Long roomId, Long memberId);

    boolean existsByMemberIdAndType(Long memberId, RoomType type);

    Page<Room> findByMemberId(Long memberId, Pageable pageable);

    List<Room> findByMemberId(Long memberId);
}
