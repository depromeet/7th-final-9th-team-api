package com.depromeet.todo.domain.task;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskStateTest {

    @Test
    void isTodo() {
        assertThat(TaskState.TODO.isTodo()).isTrue();
        assertThat(TaskState.DONE.isTodo()).isFalse();
    }
}