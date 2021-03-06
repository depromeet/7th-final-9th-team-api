package com.depromeet.todo.application.member;

import com.depromeet.todo.application.BadRequestException;
import com.depromeet.todo.application.ResourceNotFoundException;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.member.MemberFactory;
import com.depromeet.todo.domain.member.MemberRepository;
import com.depromeet.todo.domain.member.oauth.OAuthAccessToken;
import com.depromeet.todo.domain.member.oauth.OAuthService;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class MemberApplicationService {
    private final MemberRepository memberRepository;
    private final OAuthService kakaoUserService;
    private final MemberFactory memberFactory;

    @Transactional
    public Member getOrCreateMember(String accessToken) {
        Assert.hasText(accessToken, "'accessToken' must not be null, empty or blank");

        OAuthAccessToken oAuthAccessToken = OAuthAccessToken.from(accessToken);
        OAuthUserInfo oAuthUserInfo = kakaoUserService.getUserInfo(oAuthAccessToken);

        return memberRepository.findByOauthUserInfo(oAuthUserInfo)
                .orElseGet(() -> memberFactory.createMember(oAuthUserInfo));
    }

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) {
        BadRequestException.nonNull(memberId, "'memberId' must not be null");

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found. memberId:" + memberId));
    }

    @Transactional
    public Member updateMember(Long memberId, String name) {
        BadRequestException.nonNull(memberId, "'memberId' must not be null");

        return memberRepository.findById(memberId)
                .map(it -> it.updateName(name))
                .orElseThrow(() -> new ResourceNotFoundException("Member not found. memberId:" + memberId));
    }
}
