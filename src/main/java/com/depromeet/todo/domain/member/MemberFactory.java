package com.depromeet.todo.domain.member;

import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberFactory {
    private final IdGenerator snowFlakeIdGenerator;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Member createMember(OAuthUserInfo oAuthUserInfo) {
        Assert.notNull(oAuthUserInfo, "'oAuthUserInfo' must not be null");

        Member member = new Member(snowFlakeIdGenerator, oAuthUserInfo);
        memberRepository.save(member);
        applicationEventPublisher.publishEvent(
                MemberCreatedEvent.of(this, member.getMemberId())
        );
        return member;
    }
}
