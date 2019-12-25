package com.depromeet.todo.presentation.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String accessToken;
}
