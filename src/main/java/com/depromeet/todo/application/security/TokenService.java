package com.depromeet.todo.application.security;

public interface TokenService<T> {
    String generate(T memberId);

    T decode(String token);
}
