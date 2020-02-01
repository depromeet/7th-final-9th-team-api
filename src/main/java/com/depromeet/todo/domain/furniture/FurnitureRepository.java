package com.depromeet.todo.domain.furniture;

import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
    Page<Furniture> findByOwnerAndRoom(Member owner, Room room, Pageable pageable);

    Optional<Furniture> findByFurnitureIdAndOwnerAndRoom(Long furnitureId, Member owner, Room room);
}
