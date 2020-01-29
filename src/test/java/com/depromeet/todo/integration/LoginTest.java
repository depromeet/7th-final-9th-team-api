package com.depromeet.todo.integration;

import com.depromeet.todo.domain.member.oauth.OAuthService;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import com.depromeet.todo.integration.api.MemberApi;
import com.depromeet.todo.integration.api.TestApiResult;
import com.depromeet.todo.presentation.common.SuccessSimpleResponse;
import com.depromeet.todo.presentation.member.LoginResponse;
import com.depromeet.todo.presentation.member.MemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class LoginTest {
    private static final String KAKAO_ACCESS_TOKEN = "kakaoAccessToken";

    @MockBean
    private OAuthService kakaoUserService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private MemberApi memberApi;

    @BeforeEach
    void setUp() {
        memberApi = new MemberApi(mockMvc, objectMapper);
    }

    @Test
    void 로그인_후_accessToken_으로_인증이_필요한_API_요청시_성공() throws Exception {
        // given
        when(kakaoUserService.getUserInfo(any())).thenReturn(OAuthUserInfo.fromKakao("providerUserId"));
        // when,then 1
        String accessToken = this.로그인(KAKAO_ACCESS_TOKEN);
        // when,then 2
        this.내_정보_조회(accessToken);
    }

    private String 로그인(String accessToken) throws Exception {
        TestApiResult<SuccessSimpleResponse<LoginResponse>> loginResult = memberApi.login(accessToken);

        loginResult.getResultActions()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty());

        return loginResult.getApiResponse().getData().getAccessToken();
    }

    private void 내_정보_조회(String accessToken) throws Exception {
        TestApiResult<SuccessSimpleResponse<MemberResponse>> getMeResult = memberApi.getMe(accessToken);

        getMeResult.getResultActions()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNotEmpty());

        Long memberId = getMeResult.getApiResponse().getData().getId();
        assertThat(memberId).isNotNull();
    }

    @Test
    void 같은_카카오_계정으로_두_번째_로그인하는_경우_이전과_같은_accessToken_으로_응답해야함() throws Exception {
        // given
        when(kakaoUserService.getUserInfo(any())).thenReturn(OAuthUserInfo.fromKakao("providerUserId"));
        // when
        String firstAccessToken = this.로그인(KAKAO_ACCESS_TOKEN);
        String secondAccessToken = this.로그인(KAKAO_ACCESS_TOKEN);
        // then
        assertThat(firstAccessToken).isEqualTo(secondAccessToken);
    }

    @Test
    void 로그인_응답에_멤버_정보도_포함되어야함() throws Exception {
        // given
        when(kakaoUserService.getUserInfo(any())).thenReturn(OAuthUserInfo.fromKakao("providerUserId"));
        // when
        TestApiResult<SuccessSimpleResponse<LoginResponse>> loginResult = memberApi.login(KAKAO_ACCESS_TOKEN);
        // then
        loginResult.getResultActions()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.member").exists());
    }
}
