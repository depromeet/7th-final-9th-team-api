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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
        Furniture furniture = furnitureApplicationService.getFurniture(furnitureId);

        long newTask = furniture.registerTask(memberId, contents);
        return taskRepository.getOne(newTask);
    }

    @Transactional(readOnly = true)
    public Page<Tasks> getTasks(Long memberId, Pageable pageable) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(pageable, "'pageable' must not be null");

        Member member = memberApplicationService.getMember(memberId);
        return taskRepository.getByMemberIdAndStateOrderByOrder(member.getMemberId(),
                                                                  Tasks.TaskState.TODO,
                                                                  pageable);
    }

    @Transactional(readOnly = true)
    public List<Tasks> getTasksByFurniture(Long furnitureId) {
        return furnitureApplicationService.getFurniture(furnitureId)
                                          .getTasks();
    }

    public List<Tasks> getTasksByRoom(Long memberId, Long roomId) {
        Room room = roomApplicationService.getRoom(memberId, roomId);
        List<Furniture> furnitures = room.getFurnitures();
        List<Tasks> tasks = furnitures.stream()
                                        .flatMap(it -> it.getTasks().stream())
                                        .collect(Collectors.toList());
        return tasks;
    }

    public void completeTask(Long memberId, Long taskId) {
        Tasks task = taskRepository.findByIdAndMemberId(taskId, memberId)
                                   .orElseThrow(() -> new NotFoundTaskException(taskId));
        task.done();
    }
}
