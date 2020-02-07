package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.application.member.MemberApplicationService;
import com.depromeet.todo.application.security.TokenApplicationService;
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
    private final TokenApplicationService<Long> tokenApplicationService;
    private final MemberApplicationService memberApplicationService;
    private final LoginResponseAssembler loginResponseAssembler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        TodoAuthenticationToken todoAuthenticationToken = (TodoAuthenticationToken) authentication;
        Long memberId = todoAuthenticationToken.getMemberId();

        LoginResponse loginResponse = loginResponseAssembler.toLoginResponse(
                tokenApplicationService.generate(memberId),
                memberApplicationService.getMember(memberId)
        );

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        writeObjectMapper.writeValue(
                response.getOutputStream(),
                ApiResponse.successFrom(loginResponse)
        );
    }
}
