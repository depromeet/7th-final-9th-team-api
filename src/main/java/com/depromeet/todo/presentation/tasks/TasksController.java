package com.depromeet.todo.presentation.tasks;

import com.depromeet.todo.application.tasks.TasksApplicationService;
import com.depromeet.todo.domain.task.Tasks;
import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TasksController {

    private final TasksApplicationService tasksApplicationService;
    private final TaskResponseAssembler taskResponseAssembler;

    @PostMapping("/furniture/{furnitureId}/task")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TaskResponse> createTask(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long furnitureId,
            @RequestBody @Valid CreateTaskRequest createTaskRequest) {

        Tasks task = tasksApplicationService.createTask(memberId,
                                                        furnitureId,
                                                        createTaskRequest.getContents());
        TaskResponse taskResponse = taskResponseAssembler.toDisplayableTask(task);
        return ApiResponse.successFrom(taskResponse);
    }

    @GetMapping("/tasks")
    public ApiResponse<TaskResponse> getTasks(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @ApiIgnore @PageableDefault(size = 20) Pageable pageable) {
        Page<TaskResponse> taskPage = tasksApplicationService.getTasks(memberId, pageable)
                                                             .map(taskResponseAssembler::toDisplayableTask);
        return ApiResponse.successFrom(taskPage);
    }

    @GetMapping("/rooms/{roomId}/tasks")
    public ApiResponse<TaskResponse> getTasks(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long roomId) {
        List<TaskResponse> tasks = tasksApplicationService.getTasksByRoom(memberId, roomId)
                                                          .stream()
                                                          .map(taskResponseAssembler::toDisplayableTask)
                                                          .collect(Collectors.toList());
        return ApiResponse.successFrom(tasks);
    }

    @PostMapping("/tasks/{taskId}/complete")
    public ApiResponse<TaskResponse> completeTask(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long taskId) {
        Tasks task = tasksApplicationService.completeTask(memberId, taskId);
        TaskResponse taskResponse = taskResponseAssembler.toDisplayableTask(task);
        return ApiResponse.successFrom(taskResponse);
    }
}

