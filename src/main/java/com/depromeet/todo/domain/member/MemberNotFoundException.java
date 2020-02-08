package com.depromeet.todo.domain.member;

import com.depromeet.todo.domain.ResourceNotFoundException;

public class MemberNotFoundException extends ResourceNotFoundException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
