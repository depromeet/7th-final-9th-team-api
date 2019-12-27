package com.depromeet.todo.presentation.common;

import lombok.Data;

@Data
class FailureReason {
    private String resource;
    private String field;
    private String code;
}
