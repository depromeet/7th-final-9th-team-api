package com.depromeet.todo.infrastructure.spring.security;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@ToString
@EqualsAndHashCode(callSuper = false)
public class TodoAuthenticationToken extends AbstractAuthenticationToken implements MemberIdSupport<Long> {

    private final Long memberId;
    private final String accessToken;

    public TodoAuthenticationToken(Long memberId, String accessToken) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.memberId = memberId;
        this.accessToken = accessToken;
    }

    @Override
    public String getCredentials() {
        return accessToken;
    }

    @Override
    public Long getPrincipal() {
        return memberId;
    }

    @Override
    public Long getMemberId() {
        return this.getPrincipal();
    }
}
