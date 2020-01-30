package com.depromeet.todo.infrastructure.mock;

import com.depromeet.todo.application.member.MemberService;
import com.depromeet.todo.domain.member.oauth.OAuthAccessToken;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Profile("test-member")
@Aspect
@Component
@RequiredArgsConstructor
public class TestMemberAspect {
    private static final String TEST_MEMBER_ACCESS_TOKEN = "jibsuniAccessToken";
    private static final String KAKAO_ACCESS_TOKEN = "kakaoAccessToken";
    private static final String KAKAO_PROVIDER_USER_ID = "providerUserId";

    private final MemberService memberService;

    @Around("execution(* com.depromeet.todo.infrastructure.jwt.JwtService.decode(String))")
    public Object mockUser(ProceedingJoinPoint pjp) throws Throwable {
        try {
            String token = (String) pjp.getArgs()[0];
            if (TEST_MEMBER_ACCESS_TOKEN.equals(token)) {
                return Optional.of(memberService.getOrCreateMember(KAKAO_ACCESS_TOKEN).getMemberId());
            }
        } catch (Exception ignored) {
            // do nothing
        }
        return pjp.proceed();
    }

    @Around("execution(* com.depromeet.todo.infrastructure.kakao.KakaoUserService.getUserInfo(com.depromeet.todo.domain.member.oauth.OAuthCredential))")
    public Object mockKakaoUser(ProceedingJoinPoint pjp) throws Throwable {
        try {
            OAuthAccessToken oAuthAccessToken = (OAuthAccessToken) pjp.getArgs()[0];
            if (Objects.equals(KAKAO_ACCESS_TOKEN, oAuthAccessToken.getCredential())) {
                return OAuthUserInfo.fromKakao(KAKAO_PROVIDER_USER_ID);
            }
        } catch (Exception ignored) {
            // do nothing
        }
        return pjp.proceed();
    }
}
