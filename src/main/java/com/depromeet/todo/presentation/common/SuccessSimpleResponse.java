package com.depromeet.todo.presentation.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SuccessSimpleResponse<T> implements ApiResponse<T> {
    private Map<String, T> data;

    SuccessSimpleResponse(T content, String name) {
        this.data = new HashMap<>();
        data.put(name, content);
    }
}
