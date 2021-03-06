package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.application.member.LoginApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class TodoUserDetailsService implements UserDetailsService {
    private final LoginApplicationService loginApplicationService;

    @Override
    public MemberIdContainer loadUserByUsername(String kakaoAccessToken) {
        if (StringUtils.isEmpty(kakaoAccessToken)) {
            throw new BadCredentialsException("'kakaoAccessToken' must not be null or empty");
        }
        return MemberIdContainer.from(
                loginApplicationService.login(kakaoAccessToken).getMemberId()
        );
    }
}

