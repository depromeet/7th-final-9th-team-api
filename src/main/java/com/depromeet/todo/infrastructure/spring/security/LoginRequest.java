package com.depromeet.todo.infrastructure.spring.security;

import lombok.Data;

@Data
public class LoginRequest {
    private String accessToken;
}
