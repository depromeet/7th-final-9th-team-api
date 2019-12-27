package com.depromeet.todo.integration.api;

import com.depromeet.todo.presentation.common.ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

public class TestApiResult<T extends ApiResponse> {
    private final ResultActions resultActions;
    private final TypeReference<T> typeReference;
    private final ObjectMapper objectMapper;

    public TestApiResult(ResultActions resultActions,
                         TypeReference<T> typeReference,
                         ObjectMapper objectMapper) {
        this.resultActions = resultActions;
        this.typeReference = typeReference;
        this.objectMapper = objectMapper;
    }

    public ResultActions getResultActions() {
        return resultActions;
    }

    public T getApiResponse() throws IOException {
        return objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsByteArray(),
                typeReference
        );
    }
}
