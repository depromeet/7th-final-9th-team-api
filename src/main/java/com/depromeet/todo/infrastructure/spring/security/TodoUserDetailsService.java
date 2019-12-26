package com.depromeet.todo.infrastructure.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class TodoUserDetailsService implements UserDetailsService {
//    private final LoginService loginService;

    @Override
    public MemberIdContainer loadUserByUsername(String kakaoAccessToken) {
        if (StringUtils.isEmpty(kakaoAccessToken)) {
            throw new BadCredentialsException("'kakaoAccessToken' must not be null or empty");
        }
//        Member member = loginService.login(kakaoAccessToken);

        return MemberIdContainer.from(1L);
    }
}

