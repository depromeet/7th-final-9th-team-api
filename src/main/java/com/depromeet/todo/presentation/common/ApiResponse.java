package com.depromeet.todo.presentation.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ApiResponse<T> {
    static <T> ApiResponse<T> successFrom(Page<T> data) {
        return new SuccessPageResponse<>(
                data.getContent(),
                data.getTotalElements()
        );
    }

    static <T> ApiResponse<T> successFrom(Slice<T> data) {
        return new SuccessSliceResponse<>(
                data.getContent(),
                data.hasNext()
        );
    }

    static <T> ApiResponse<T> successFrom(List<T> data) {
        return new SuccessListResponse<>(data);
    }

    static <T> ApiResponse<T> successFrom(T data) {
        return new SuccessSimpleResponse<>(data);
    }

    static ApiResponse failureFrom(String message) {
        return new FailureResponse(message, null);
    }
}
