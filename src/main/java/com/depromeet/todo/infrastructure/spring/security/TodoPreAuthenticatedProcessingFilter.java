package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.application.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class TodoPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final Pattern AUTHORIZATION_HEADER_PATTERN = Pattern.compile("^[Bb]earer (.*)$");

    private final TokenService<Long> tokenService;

    @Override
    protected String getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER_NAME))
                .map(AUTHORIZATION_HEADER_PATTERN::matcher)
                .map(matcher -> matcher.matches() ? matcher.group(1) : null)
                .orElse(null);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        // do nothing
        return null;
    }
}
