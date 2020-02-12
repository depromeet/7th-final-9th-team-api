package com.depromeet.todo.domain.task;

import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.furniture.FurnitureType;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomRepository;
import com.depromeet.todo.domain.room.RoomType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

@DataJpaTest
class TaskRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(TaskRepositoryTest.class);

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TaskRepository taskRepository;

    // todo : test code
    @Test
    void findByStateAndDeadlineBefore() {
/*        Furniture furniture = Furniture.of(1L, FurnitureType.BED);
        Room room = Room.of(1L, 1L, RoomType.BEDROOM, Arrays.asList(furniture));
        roomRepository.save(room);

        Tasks task = Tasks.of(1L, furniture,
                                  "잠자기", 0);
        taskRepository.save(task);

        LocalDateTime now = LocalDateTime.now();
        List<Tasks> actual = taskRepository.findByStateAndDeadlineBefore(Tasks.TaskState.TODO, now);
        log.info("result : {} ", actual);

        assertThat(actual).hasSize(1);*/
    }
}