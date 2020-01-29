package com.depromeet.todo.integration;

import com.depromeet.todo.presentation.member.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test-member")
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class TestMemberTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 테스트_토큰을_입력하면__인증에_성공해야_합니다() throws Exception {
        // given
        String testToken = "jibsuniAccessToken";
        // when
        mockMvc.perform(
                get("/api/members/me")
                        .header("Authorization", "bearer " + testToken))
                // then
                .andExpect(status().isOk());
    }

    @Test
    void 테스트_카카오_토큰을_입력하면__테스트_멤버로_응답합니다() throws Exception {
        // given
        String testKakaoToken = "kakaoAccessToken";
        // when
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccessToken(testKakaoToken);
        mockMvc.perform(
                post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(loginRequest)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.member").exists())
                .andExpect(jsonPath("$.data.member.id").value("1"));

    }
}
