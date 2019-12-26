package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.application.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper writeObjectMapper;
    private final TokenService<Long> tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        TodoAuthenticationToken todoAuthenticationToken = (TodoAuthenticationToken) authentication;
        String accessToken = tokenService.generate(
                todoAuthenticationToken.getMemberId()
        );

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // TODO: 공통 응답 모듈 머지되면 payload 수정해야함
        writeObjectMapper.writeValue(response.getOutputStream(), accessToken);
    }
}
