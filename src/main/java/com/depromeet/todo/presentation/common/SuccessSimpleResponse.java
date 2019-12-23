package com.depromeet.todo.presentation.common;

import lombok.Data;

@Data
class SuccessSimpleResponse<T> implements ApiResponse<T> {
    private final T data;
}
