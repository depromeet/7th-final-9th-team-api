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
            throw new BadCredentialsException("invalid kakaoAccessToken");
        }

        UserDetails userDetails = todoUserDetailsService.loadUserByUsername(kakaoAccessToken);
        token.setPrincipal(userDetails.getUsername());
        token.setUserId(Integer.parseInt(userDetails.getUsername()));
        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return KakaoAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

