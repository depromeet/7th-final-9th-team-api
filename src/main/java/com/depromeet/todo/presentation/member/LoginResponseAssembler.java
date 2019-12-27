package com.depromeet.todo.presentation.member;

import org.springframework.stereotype.Component;

@Component
public class LoginResponseAssembler {
    public LoginResponse toLoginResponse(String accessToken) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        return loginResponse;
    }
}
