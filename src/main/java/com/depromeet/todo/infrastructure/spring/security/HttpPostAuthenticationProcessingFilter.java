package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.presentation.member.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
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

@Slf4j
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
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        MediaType mediaType = this.resolveMediaType(request);
        if (!MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
            return null;
        }
        LoginRequest loginRequest = this.resolvePayload(request);

        String kakaoAccessToken = Optional.ofNullable(loginRequest)
                .map(LoginRequest::getAccessToken)
                .map(String::trim)
                .orElse("");

        AbstractAuthenticationToken authRequest = new KakaoAuthenticationToken(kakaoAccessToken);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private MediaType resolveMediaType(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType == null) {
            return null;
        }
        try {
            return MediaType.valueOf(contentType);
        } catch (InvalidMediaTypeException ex) {
            log.warn("Failed to resolve mediaType. contentType: {}", contentType, ex);
            return null;
        }
    }

    private LoginRequest resolvePayload(HttpServletRequest request) {
        try {
            return readObjectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException ex) {
            log.warn("Failed to parse login request body.", ex);
            return null;
        }
    }
}
