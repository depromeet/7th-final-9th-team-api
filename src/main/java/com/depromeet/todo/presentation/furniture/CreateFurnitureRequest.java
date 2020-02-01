package com.depromeet.todo.presentation.furniture;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class CreateFurnitureRequest {
    @NotBlank
    private String furnitureType;
}
