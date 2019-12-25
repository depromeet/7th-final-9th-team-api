package com.depromeet.todo.presentation.login;

import com.depromeet.todo.application.member.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/members/login/kakao")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        String accessToken = loginRequest.getAccessToken();
        loginService.login(accessToken);
        return new LoginResponse();
    }
}
