package com.depromeet.todo.presentation.tasks;

import com.depromeet.todo.application.tasks.TasksApplicationService;
import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TasksController {

    private final TasksApplicationService tasksApplicationService;
    private final TaskResponseAssembler taskResponseAssembler;

    @PostMapping("/furniture/{furnitureId}/task")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createRoom(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long furnitureId,
            @RequestBody @Valid CreateTaskRequest createTaskRequest) {

        long task = tasksApplicationService.createTask(memberId,
                                                       furnitureId,
                                                       createTaskRequest.getContents());
        return ApiResponse.successFrom(task);
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
        return null;
    }

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity completeTask(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long taskId) {
        tasksApplicationService.completeTask(memberId, taskId);

        return ResponseEntity.ok().build();
    }
}

