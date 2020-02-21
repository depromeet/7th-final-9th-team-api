package com.depromeet.todo.presentation.tasks;

import com.depromeet.todo.application.tasks.DisplayableTaskAssembler;
import com.depromeet.todo.domain.task.Task;
import com.depromeet.todo.domain.task.TaskResults;
import com.depromeet.todo.domain.task.TaskState;
import org.springframework.stereotype.Component;

@Component
public class TaskResponseAssembler implements DisplayableTaskAssembler {

    @Override
    public TaskResponse toDisplayableTask(Task task) {
        if (task == null) {
            return null;
        }
        return TaskResponse.builder()
                           .id(task.getId())
                           .furnitureName(task.getFurniture()
                                              .getFurnitureType()
                                              .getName())
                           .contents(task.getContents())
                           .state(task.getState())
                           .order(task.getDisplayOrder())
                           .deadline(task.getDeadline())
                           .createdAt(task.getCreatedAt())
                           .build();
    }


    CountOfTasksResponse toDisplayableTask(TaskResults taskResults) {
        return CountOfTasksResponse.builder()
                                   .countOfDone(taskResults.getTotalTasks(TaskState.DONE))
                                   .countOfTodo(taskResults.getTotalTasks(TaskState.TODO))
                                   .total(taskResults.getTotalTasks())
                                   .build();
    }
}