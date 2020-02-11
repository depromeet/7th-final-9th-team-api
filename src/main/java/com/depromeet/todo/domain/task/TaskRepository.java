package com.depromeet.todo.domain.task;

import com.depromeet.todo.domain.furniture.Furniture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks, Long> {

    List<Tasks> getByFurniture(Furniture furniture);

    Page<Tasks> getByMemberIdAndStateOrderByOrder(long memberId, Tasks.TaskState state, Pageable pageable);
}