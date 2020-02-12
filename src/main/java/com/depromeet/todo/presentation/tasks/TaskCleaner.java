package com.depromeet.todo.presentation.tasks;

import com.depromeet.todo.application.tasks.TasksApplicationService;
import com.depromeet.todo.domain.task.Tasks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class TaskCleaner {

    private final TasksApplicationService tasksApplicationService;

    public TaskCleaner(TasksApplicationService tasksApplicationService) {
        log.info("마감일 지난 할일정리 = ON");
        this.tasksApplicationService = tasksApplicationService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void executeImpleteTask() {
        LocalDateTime now = LocalDateTime.now();
        List<Tasks> tasks = tasksApplicationService.changeCompleteTaskOverDeadline(now);
        log.info("미완료한 할일 {}개 정리: {}", tasks.size(), tasks);
    }
}