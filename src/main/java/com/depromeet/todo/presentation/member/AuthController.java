package com.depromeet.todo.presentation.member;

import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Profile("swagger")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/members/login")
    public ApiResponse<LoginResponse> login(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @RequestBody LoginRequest loginRequest
    ) {
        return null;
    }

    @PostMapping("/members/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<LoginResponse> logout(
            @RequestHeader(required = false, name = "Authorization") String authorization
    ) {
        return null;
    }
}
