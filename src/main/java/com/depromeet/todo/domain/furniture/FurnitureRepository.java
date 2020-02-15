package com.depromeet.todo.domain.furniture;

import com.depromeet.todo.domain.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
    Page<Furniture> findByMemberIdAndRoom(Long memberId, Room room, Pageable pageable);

    Optional<Furniture> findByIdAndMemberId(Long id, Long memberId);
}
