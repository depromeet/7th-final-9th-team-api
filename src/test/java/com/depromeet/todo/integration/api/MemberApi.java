package com.depromeet.todo.integration.api;

import com.depromeet.todo.presentation.member.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class MemberApi {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public MemberApi(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public ResultActions login(String kakaoAccessToken) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccessToken(kakaoAccessToken);

        return mockMvc.perform(
                post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(loginRequest))
        );
    }

    public ResultActions getMe(String accessToken) throws Exception {
        return mockMvc.perform(
                get("/api/members/me")
                        .header("Authorization", "bearer " + accessToken)
        );

    }
}
