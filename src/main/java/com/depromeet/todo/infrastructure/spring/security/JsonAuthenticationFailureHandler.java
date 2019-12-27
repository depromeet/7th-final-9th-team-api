package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.presentation.common.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper writeObjectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        writeObjectMapper.writeValue(
                response.getOutputStream(),
                ApiResponse.failureFrom(
                        this.resolveMessage(exception)
                )
        );
    }

    private String resolveMessage(AuthenticationException exception) {
        if (exception instanceof UsernameNotFoundException) {
            return "해당 token 으로 가입한 회원이 존재하지 않습니다. " + exception.getMessage();
        }
        return "입력한 token 정보가 올바르지 않습니다. " + exception.getMessage();
    }
}

