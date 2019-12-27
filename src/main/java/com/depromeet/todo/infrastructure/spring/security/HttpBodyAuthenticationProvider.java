package com.depromeet.todo.infrastructure.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class HttpBodyAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService todoUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!this.supports(authentication.getClass())) {
            return null;
        }
        KakaoAuthenticationToken token = (KakaoAuthenticationToken) authentication;
        String kakaoAccessToken = token.getKakaoAccessToken();
        if (StringUtils.isEmpty(kakaoAccessToken)) {
            throw new BadCredentialsException("'kakaoAccessToken' must not be null or empty");
        }

        UserDetails userDetails = todoUserDetailsService.loadUserByUsername(kakaoAccessToken);
        MemberIdContainer memberIdContainer = (MemberIdContainer) userDetails;
        return new TodoAuthenticationToken(memberIdContainer.getMemberId(), kakaoAccessToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return KakaoAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

