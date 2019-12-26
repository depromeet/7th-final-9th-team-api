package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.application.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@RequiredArgsConstructor
public class PreAuthTokenAuthenticationProvider implements AuthenticationProvider {
    private final TokenService<Long> tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!this.supports(authentication.getClass())) {
            return null;
        }
        PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
        Object principal = preAuthenticatedAuthenticationToken.getPrincipal();
        if (!(principal instanceof String)) {
            return null;
        }
        String accessToken = (String) principal;
        Long memberId = tokenService.decode(accessToken);
        TodoAuthenticationToken todoAuthenticationToken = new TodoAuthenticationToken(memberId, accessToken);
        todoAuthenticationToken.setAuthenticated(true);
        return todoAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
