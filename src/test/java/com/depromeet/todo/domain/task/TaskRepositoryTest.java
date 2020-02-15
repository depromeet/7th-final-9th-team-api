package com.depromeet.todo.domain.task;

import com.depromeet.todo.domain.room.RoomRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
/*        Furniture furnitures = Furniture.of(1L, FurnitureType.BED);
        Room room = Room.of(1L, 1L, RoomType.BEDROOM, Arrays.asList(furnitures));
        roomRepository.save(room);

        Task task = Task.of(1L, furnitures,
                                  "잠자기", 0);
        taskRepository.save(task);

        LocalDateTime now = LocalDateTime.now();
        List<Task> actual = taskRepository.findByStateAndDeadlineBefore(Task.TaskState.TODO, now);
        log.info("result : {} ", actual);

        assertThat(actual).hasSize(1);*/
    }
}