package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.presentation.member.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class HttpPostAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper readObjectMapper;

    public HttpPostAuthenticationProcessingFilter(String pattern, HttpMethod httpMethod, ObjectMapper readObjectMapper) {
        super(new AntPathRequestMatcher(pattern, httpMethod.name()));
        this.readObjectMapper = readObjectMapper;
    }

    /**
     * Performs actual authentication.
     * <p>
     * The implementation should do one of the following:
     * <ol>
     * <li>Return a populated authentication token for the authenticated user, indicating
     * successful authentication</li>
     * <li>Return null, indicating that the authentication process is still in progress.
     * Before returning, the implementation should perform any additional work required to
     * complete the process.</li>
     * <li>Throw an <tt>AuthenticationException</tt> if the authentication process fails</li>
     * </ol>
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OpenID).
     * @return the authenticated user token, or null if authentication is incomplete.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!MediaType.APPLICATION_JSON.isCompatibleWith(MediaType.valueOf(request.getContentType()))) {
            return null;
        }
        LoginRequest loginRequest = readObjectMapper.readValue(request.getInputStream(), LoginRequest.class);

        String kakaoAccessToken = Optional.ofNullable(loginRequest)
                .map(LoginRequest::getAccessToken)
                .map(String::trim)
                .orElse("");

        AbstractAuthenticationToken authRequest = new KakaoAuthenticationToken(kakaoAccessToken);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *                    set
     */
    private void setDetails(HttpServletRequest request,
                            AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
