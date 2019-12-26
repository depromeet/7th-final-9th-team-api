package com.depromeet.todo.application;

import org.springframework.stereotype.Service;

@Service
public class MockTokenService implements TokenService<Long> {
    @Override
    public String generate(Long memberId) {
        return null;
    }

    @Override
    public Long decode(String token) {
        if (token == null) {
            return null;
        }
        if (token.equals("todoAccessToken")) {
            return 2L;
        }
        return null;
    }
}
