package com.depromeet.todo.application.member;

import com.depromeet.todo.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class LoginApplicationService {
    private final MemberApplicationService memberApplicationService;

    @Transactional
    public Member login(String accessToken) {
        Assert.hasText(accessToken, "'accessToken' must not be null, empty or blank");

        return memberApplicationService.getOrCreateMember(accessToken);
    }
}
