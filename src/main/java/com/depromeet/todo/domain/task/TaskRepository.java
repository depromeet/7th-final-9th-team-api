package com.depromeet.todo.domain.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByMemberIdOrderByDisplayOrder(long memberId, Pageable pageable);

    Optional<Task> findByIdAndMemberId(long id, long memberId);

    List<Task> findByStateAndDeadlineBefore(TaskState state, LocalDateTime now);
}