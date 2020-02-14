package com.depromeet.todo.presentation.tasks;

import com.depromeet.todo.application.Displayable;
import com.depromeet.todo.domain.furniture.FurnitureType;
import com.depromeet.todo.domain.task.TaskState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse implements Displayable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String furnitureName;
    private Integer order;
    private TaskState state;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
}