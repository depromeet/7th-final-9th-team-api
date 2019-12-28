package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.application.security.TokenService;
import com.depromeet.todo.presentation.common.ApiResponse;
import com.depromeet.todo.presentation.member.LoginResponse;
import com.depromeet.todo.presentation.member.LoginResponseAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper writeObjectMapper;
    private final TokenService<Long> tokenService;
    private final LoginResponseAssembler loginResponseAssembler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        TodoAuthenticationToken todoAuthenticationToken = (TodoAuthenticationToken) authentication;
        String accessToken = tokenService.generate(
                todoAuthenticationToken.getMemberId()
        );
        LoginResponse loginResponse = loginResponseAssembler.toLoginResponse(accessToken);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        writeObjectMapper.writeValue(
                response.getOutputStream(),
                ApiResponse.successFrom(loginResponse)
        );
    }
}
