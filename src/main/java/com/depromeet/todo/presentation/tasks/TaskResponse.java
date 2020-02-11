package com.depromeet.todo.presentation.tasks;

import com.depromeet.todo.application.Displayable;
import com.depromeet.todo.domain.task.Tasks;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse implements Displayable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private Long furnitureId;
    private Integer order;
    private Tasks.TaskState state;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
}