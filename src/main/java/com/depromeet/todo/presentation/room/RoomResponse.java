package com.depromeet.todo.presentation.room;

import com.depromeet.todo.application.Displayable;
import com.depromeet.todo.presentation.furniture.FurnitureResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomResponse implements Displayable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String type;
    private List<FurnitureResponse> furnitures = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
