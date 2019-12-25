package com.depromeet.todo.application.member;

import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.member.oauth.OAuthAccessToken;
import com.depromeet.todo.domain.member.oauth.OAuthService;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CreateMember_Auditing_Test {
    private static final String KAKAO_ACCESS_TOKEN = "kakaoAccessToken";

    @MockBean
    private IdGenerator snowFlakeIdGenerator;
    @MockBean
    private OAuthService kakaoUserService;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        when(snowFlakeIdGenerator.generate()).thenReturn(1L);
        when(kakaoUserService.getUserInfo(OAuthAccessToken.from(KAKAO_ACCESS_TOKEN))).thenReturn(OAuthUserInfo.fromKakao("2"));
    }

    @Test
    void 멤버를_생성하면_created_at_이_잘_설정되는지() {
        // given
        LocalDateTime beforeMemberCreated = LocalDateTime.now();
        // when
        Member member = memberService.getOrCreateMember(KAKAO_ACCESS_TOKEN);
        // then
        assertThat(member.getCreatedAt()).isAfter(beforeMemberCreated);
    }

    @Test
    void 멤버를_생성하면_updated_at_이_잘_설정되는지() {
        // given
        LocalDateTime beforeMemberCreated = LocalDateTime.now();
        // when
        Member member = memberService.getOrCreateMember(KAKAO_ACCESS_TOKEN);
        // then
        assertThat(member.getUpdatedAt()).isAfter(beforeMemberCreated);
    }
}

