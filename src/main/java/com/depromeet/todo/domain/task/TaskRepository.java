package com.depromeet.todo.domain.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Tasks, Long> {

    Page<Tasks> findByMemberIdAndStateOrderByDisplayOrder(long memberId, TaskState state, Pageable pageable);

    Optional<Tasks> findByIdAndMemberId(long id, long memberId);

    List<Tasks> findByStateAndDeadlineBefore(TaskState state, LocalDateTime now);
}