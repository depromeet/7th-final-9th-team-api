package com.depromeet.todo.application;

import java.util.Optional;

public interface TokenService<T> {
    String generate(T memberId);

    Optional<T> decode(String token);
}