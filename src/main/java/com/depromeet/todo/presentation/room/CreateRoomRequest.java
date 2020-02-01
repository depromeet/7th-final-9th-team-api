package com.depromeet.todo.presentation.room;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class CreateRoomRequest {
    @NotBlank
    private String roomType;
}
