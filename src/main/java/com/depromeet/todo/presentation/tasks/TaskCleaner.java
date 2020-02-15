package com.depromeet.todo.presentation.tasks;

import com.depromeet.todo.application.tasks.TasksApplicationService;
import com.depromeet.todo.domain.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(value = "task-cleaner.execute", havingValue = "true")
public class TaskCleaner {

    private final TasksApplicationService tasksApplicationService;

    public TaskCleaner(TasksApplicationService tasksApplicationService) {
        this.tasksApplicationService = tasksApplicationService;
    }

    @Scheduled(cron = "${task-cleaner.cron}")
    public void execute() {
        LocalDateTime now = LocalDate.now()
                                     .atTime(LocalTime.MAX);
        List<Task> tasks = tasksApplicationService.changeCompleteTaskOverDeadline(now);
        log.info("미완료한 할일 {}개 정리: {}", tasks.size(), tasks.stream()
                                                          .map(Task::getId)
                                                          .toArray());
    }
}