package com.depromeet.todo.presentation.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SuccessPageResponse<T> implements ApiResponse<T> {
    private List<T> data;
    private Long totalCount;
}
