package com.depromeet.todo.application.tasks;

import com.depromeet.todo.application.furniture.FurnitureApplicationService;
import com.depromeet.todo.application.member.MemberApplicationService;
import com.depromeet.todo.application.room.RoomApplicationService;
import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomRepository;
import com.depromeet.todo.domain.task.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.depromeet.todo.domain.task.TaskState.TODO;

@Service
@Transactional
@RequiredArgsConstructor
public class TasksApplicationService {

    private final RoomApplicationService roomApplicationService;
    private final MemberApplicationService memberApplicationService;
    private final FurnitureApplicationService furnitureApplicationService;
    private final RoomRepository roomRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public Task createTask(Long memberId,
                           Long furnitureId,
                           String contents) {
        memberApplicationService.getMember(memberId);
        Furniture furniture = furnitureApplicationService.getFurnitures(furnitureId);

        Task task = furniture.registerTask(memberId, contents);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Page<Task> getTasks(Long memberId, Pageable pageable) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(pageable, "'pageable' must not be null");

        Member member = memberApplicationService.getMember(memberId);
        return taskRepository.findByMemberIdOrderByDisplayOrder(member.getMemberId(), pageable);
    }

    public Task completeTask(Long memberId, Long taskId) {
        Task task = taskRepository.findByIdAndMemberId(taskId, memberId)
                                  .orElseThrow(() -> new NotFoundTaskException(taskId));
        task.done();
        return task;
    }

    public Task incompleteTask(Long memberId, Long taskId) {
        Task task = taskRepository.findByIdAndMemberId(taskId, memberId)
                                  .orElseThrow(() -> new NotFoundTaskException(taskId));
        task.todo();
        return task;
    }

    public List<Task> changeCompleteTaskOverDeadline(LocalDateTime now) {
        List<Task> tasks = taskRepository.findByStateAndDeadlineBefore(TODO, now);
        tasks.forEach(Task::done);
        return tasks;
    }

    public List<Task> getTasksByRoom(Long memberId, Long roomId) {
        Room room = roomApplicationService.getRoom(memberId, roomId);
        return room.getFurniture()
                   .stream()
                   .flatMap(it -> it.getTasks()
                                    .stream())
                   .collect(Collectors.toList());
    }

    public TaskResults getCountOfTask(Long memberId) {
        List<Room> rooms = roomRepository.findByMemberId(memberId);

        TaskResults taskResults = new TaskResults();
        for (Room room : rooms) {
            List<Task> tasks = room.getTasks();
            List<TaskCount> taskCounts = Arrays.stream(TaskState.values())
                                               .map(it -> new TaskCount(it, it.getTotalCount(tasks)))
                                               .collect(Collectors.toList());
            taskResults.add(room, taskCounts);
        }
        return taskResults;
    }
}
