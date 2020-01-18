package com.depromeet.todo.infrastructure.mock;

import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.member.MemberRepository;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;

@Slf4j
@Profile("test-member")
@Component
@RequiredArgsConstructor
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            Constructor<Member> constructor = Member.class.getDeclaredConstructor(Long.class, OAuthUserInfo.class);
            constructor.setAccessible(true);
            Member member = constructor.newInstance(
                    1L,
                    OAuthUserInfo.fromKakao("providerUserId")
            );
            memberRepository.save(member);
        } catch (Exception ex) {
            log.error("Failed to create test member", ex);
        }
    }
}
