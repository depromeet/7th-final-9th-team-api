package com.depromeet.todo.application.member;

import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.member.MemberFactory;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberUpdateNameTest {
    @Autowired
    private MemberFactory memberFactory;

    @Test
    void 이름_업데이트() {
        // given
        OAuthUserInfo oAuthUserInfo = OAuthUserInfo.fromKakao("providerUserId");
        Member member = memberFactory.createMember(oAuthUserInfo);
        // when
        member.updateName("updatedName");
        // then
        assertThat(member.getName()).isEqualTo("updatedName");
    }
}
