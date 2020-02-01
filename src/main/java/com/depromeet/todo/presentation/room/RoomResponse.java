package com.depromeet.todo.presentation.room;

import com.depromeet.todo.application.Displayable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomResponse implements Displayable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
