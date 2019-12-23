package com.depromeet.todo.presentation.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FailureResponseTest {
    @Test
    void failureFrom() {
        ApiResponse apiResponse = ApiResponse.failureFrom("Failed something");

        FailureResponse failureResponse = (FailureResponse) apiResponse;
        assertThat(failureResponse.getMessage()).isEqualTo("Failed something");
    }
}
