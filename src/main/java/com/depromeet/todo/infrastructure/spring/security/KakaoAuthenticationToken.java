package com.depromeet.todo.infrastructure.spring.security;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@ToString
@EqualsAndHashCode(callSuper = false)
public class KakaoAuthenticationToken extends AbstractAuthenticationToken {
    private static final String EMPTY_CREDENTIALS = "";

    private final String kakaoAccessToken;
    private String principal;
    private Integer userId;

    public KakaoAuthenticationToken(String kakaoAccessToken) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.kakaoAccessToken = kakaoAccessToken;
    }

    public String getKakaoAccessToken() {
        return kakaoAccessToken;
    }

    @Override
    public String getCredentials() {
        return EMPTY_CREDENTIALS;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
