package com.depromeet.todo.application.tasks;

import com.depromeet.todo.application.furniture.FurnitureApplicationService;
import com.depromeet.todo.application.member.MemberApplicationService;
import com.depromeet.todo.application.room.RoomApplicationService;
import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.member.Member;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.task.TaskRepository;
import com.depromeet.todo.domain.task.Tasks;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
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
    private final TaskRepository taskRepository;

    @Transactional
    public Tasks createTask(Long memberId,
                            Long furnitureId,
                            String contents) {
        memberApplicationService.getMember(memberId);
        Furniture furniture = furnitureApplicationService.getFurnitures(furnitureId);

        Tasks task = furniture.registerTask(memberId, contents);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Page<Tasks> getTasks(Long memberId, Pageable pageable) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(pageable, "'pageable' must not be null");

        Member member = memberApplicationService.getMember(memberId);
        return taskRepository.findByMemberIdAndStateOrderByDisplayOrder(member.getMemberId(),
                                                                        TODO,
                                                                        pageable);
    }

    public Tasks completeTask(Long memberId, Long taskId) {
        Tasks task = taskRepository.findByIdAndMemberId(taskId, memberId)
                                   .orElseThrow(() -> new NotFoundTaskException(taskId));
        task.done();
        return task;
    }

    public List<Tasks> changeCompleteTaskOverDeadline(LocalDateTime now) {
        List<Tasks> tasks = taskRepository.findByStateAndDeadlineBefore(TODO, now);
        tasks.forEach(Tasks::done);
        return tasks;
    }

    public List<Tasks> getTasksByRoom(Long memberId, Long roomId) {
        Room room = roomApplicationService.getRoom(memberId, roomId);
        return room.getFurniture()
                   .stream()
                   .flatMap(it -> it.getTasks()
                                    .stream())
                   .filter(Tasks::isTodo)
                   .collect(Collectors.toList());
    }
}
