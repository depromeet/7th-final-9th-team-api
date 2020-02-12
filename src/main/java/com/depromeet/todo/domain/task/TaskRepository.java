package com.depromeet.todo.domain.task;

import com.depromeet.todo.domain.furniture.Furniture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Tasks, Long> {

    List<Tasks> getByFurniture(Furniture furniture);

    Page<Tasks> getByMemberIdAndStateOrderByOrdered(long memberId, Tasks.TaskState state, Pageable pageable);

    Optional<Tasks> findByIdAndMemberId(long id, long memberId);

    List<Tasks> findByStateAndDeadlineBefore(Tasks.TaskState state, LocalDateTime now);
}