package com.depromeet.todo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@ActiveProfiles("test-member")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class CorsTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void CORS_확인을_위헤_OPTIONS_메서드를_사용할_수_있는지() throws Exception {
        // given
        String origin = "http://localhost:3000";
        // when
        mockMvc.perform(options("/api/members/me")
                .header("Origin", origin)
                .header("Access-Control-Request-Headers", "Origin, Accept, Content-Type, Authorization")
                .header("Access-Control-Request-Method", "GET"))
                // then
                .andExpect(status().isOk());
    }
}
