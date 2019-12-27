package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.application.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Optional;

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
        Optional<Long> memberIdOptional = tokenService.decode(accessToken);
        if (!memberIdOptional.isPresent()) {
            return null;
        }
        TodoAuthenticationToken todoAuthenticationToken = new TodoAuthenticationToken(
                memberIdOptional.get(),
                accessToken
        );
        todoAuthenticationToken.setAuthenticated(true);
        return todoAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
