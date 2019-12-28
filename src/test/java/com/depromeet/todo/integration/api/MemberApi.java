package com.depromeet.todo.integration.api;

import com.depromeet.todo.presentation.common.SuccessSimpleResponse;
import com.depromeet.todo.presentation.member.LoginRequest;
import com.depromeet.todo.presentation.member.LoginResponse;
import com.depromeet.todo.presentation.member.MemberResponse;
import com.fasterxml.jackson.core.type.TypeReference;
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

    public TestApiResult<SuccessSimpleResponse<LoginResponse>> login(String kakaoAccessToken) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccessToken(kakaoAccessToken);

        ResultActions resultActions = mockMvc.perform(
                post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(loginRequest))
        );
        return new TestApiResult<>(
                resultActions,
                new TypeReference<SuccessSimpleResponse<LoginResponse>>() {
                },
                objectMapper
        );
    }

    public TestApiResult<SuccessSimpleResponse<MemberResponse>> getMe(String accessToken) throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/api/members/me")
                        .header("Authorization", "bearer " + accessToken)
        );
        return new TestApiResult<>(
                resultActions,
                new TypeReference<SuccessSimpleResponse<MemberResponse>>() {
                },
                objectMapper
        );
    }
}
