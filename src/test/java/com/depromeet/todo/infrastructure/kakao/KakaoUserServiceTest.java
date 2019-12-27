package com.depromeet.todo.infrastructure.kakao;

import com.depromeet.todo.domain.member.oauth.OAuthAccessToken;
import com.depromeet.todo.domain.member.oauth.OAuthCredential;
import com.depromeet.todo.domain.member.oauth.OAuthService;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class KakaoUserServiceTest {
    @Autowired
    private OAuthService KakaoUserService;

    @Disabled // kakao api 를 직접 호출하는 테스트이므로, 평소에는 disabled 합니다
    @Test
    void kakao_멤버_정보_api_가_잘_동작하는지() {
        // given
        OAuthCredential oAuthCredential = OAuthAccessToken.from("l2QoGfAlOyb74MW9XAY3gDGhp_hTK6OPb8ydFQoqAq8AAAFvPFcWmA");
        // when
        OAuthUserInfo oAuthUserInfo = KakaoUserService.getUserInfo(oAuthCredential);
        // then
        assertThat(oAuthUserInfo).isNotNull();
    }
}