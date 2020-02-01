package com.depromeet.todo.presentation.common;

import com.depromeet.todo.presentation.member.MemberResponse;
import com.depromeet.todo.presentation.room.RoomResponse;
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
        if (data instanceof MemberResponse) {
            return new SuccessSimpleResponse<>(data, "member");
        }
        if (data instanceof RoomResponse) {
            return new SuccessSimpleResponse<>(data, "room");
        }
        return new SuccessMapResponse<>(data);
    }

    static <T> ApiResponse<T> successOf(T data, String name) {
        return new SuccessSimpleResponse<>(data, name);
    }

    static ApiResponse<String> failureFrom(String message) {
        return new FailureResponse(message, null);
    }
}
