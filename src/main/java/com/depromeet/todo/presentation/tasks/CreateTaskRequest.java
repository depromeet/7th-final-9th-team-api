package com.depromeet.todo.presentation.tasks;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class CreateTaskRequest {

    @NotBlank
    private String contents;
}
