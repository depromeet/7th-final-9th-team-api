package com.depromeet.todo.application.security;

import org.springframework.stereotype.Service;

@Service
public class MockTokenService implements TokenService<Long> {
    @Override
    public String generate(Long memberId) {
        return null;
    }

    @Override
    public Long decode(String token) {
        if ("todoAccessToken".equals(token)) {
            return 2L;
        }
        return null;
    }
}
