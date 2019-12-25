package com.depromeet.todo.infrastructure.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class TodoUserDetailsService implements UserDetailsService {
//    private final LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String kakaoAccessToken) throws AuthenticationException {
        if (StringUtils.isEmpty(kakaoAccessToken)) {
            throw new BadCredentialsException("'kakaoAccessToken' must not be null or empty");
        }
//        Member member = loginService.login(kakaoAccessToken);

        return User.builder()
//                .username(String.valueOf(member.getMemberId()))
                .username("1")
                .password("")
                .roles("USER")
                .build();
    }
}

