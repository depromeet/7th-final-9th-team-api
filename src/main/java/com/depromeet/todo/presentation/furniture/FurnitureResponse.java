package com.depromeet.todo.presentation.furniture;

import com.depromeet.todo.application.Displayable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FurnitureResponse implements Displayable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
