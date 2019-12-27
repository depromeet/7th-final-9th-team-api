package com.depromeet.todo.presentation.common;

import lombok.Data;

import java.util.List;

@Data
class SuccessListResponse<T> implements ApiResponse<T> {
    private final List<T> data;
}
