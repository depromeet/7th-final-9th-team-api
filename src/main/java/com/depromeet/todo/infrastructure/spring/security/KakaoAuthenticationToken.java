package com.depromeet.todo.infrastructure.spring.security;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@ToString
@EqualsAndHashCode(callSuper = false)
public class KakaoAuthenticationToken extends AbstractAuthenticationToken {

    private final String kakaoAccessToken;

    public KakaoAuthenticationToken(String kakaoAccessToken) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.kakaoAccessToken = kakaoAccessToken;
    }

    public String getKakaoAccessToken() {
        return this.getPrincipal();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return kakaoAccessToken;
    }
}
