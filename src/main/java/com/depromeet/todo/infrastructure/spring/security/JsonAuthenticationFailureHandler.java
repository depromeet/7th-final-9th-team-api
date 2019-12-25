package com.depromeet.todo.infrastructure.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper writeObjectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof UsernameNotFoundException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writeObjectMapper.writeValue(response.getOutputStream(), "해당 token 으로 가입한 회원이 존재하지 않습니다");
            return;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // TODO: 공통 응답 모듈 머지되면 responseBody 수정하기
        writeObjectMapper.writeValue(response.getOutputStream(), "입력한 token 정보가 올바르지 않습니다");
    }
}

