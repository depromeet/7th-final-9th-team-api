package com.depromeet.todo.presentation.tasks;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
class CreateTaskRequest {

    @NotNull
    private Long memberId;
    @NotNull
    private Long furnitureId;
    @NotBlank
    private String contents;
}
