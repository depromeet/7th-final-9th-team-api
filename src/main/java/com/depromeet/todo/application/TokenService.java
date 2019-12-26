package com.depromeet.todo.application;

public interface TokenService<T> {
    String generate(T memberId);

    T decode(String token);
}
