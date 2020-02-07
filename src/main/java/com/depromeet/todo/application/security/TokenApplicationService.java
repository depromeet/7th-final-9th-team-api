package com.depromeet.todo.application.security;

import java.util.Optional;

public interface TokenApplicationService<T> {
    String generate(T memberId);

    Optional<T> decode(String token);
}