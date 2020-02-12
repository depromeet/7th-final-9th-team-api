package com.depromeet.todo.infrastructure.mock;

import com.depromeet.todo.domain.HouseBuilder;
import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.furniture.FurnitureRepository;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.task.TaskRepository;
import com.depromeet.todo.domain.task.Tasks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    private HouseBuilder houseBuilder;
    @Autowired
    private FurnitureRepository furnitureRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Room> rooms = houseBuilder.build(1L);
        log.info("Create rooms : {}", rooms);

        Furniture furniture = rooms.get(0)
                                   .getFurniture()
                                   .get(0);
        Tasks task = furniture.registerTask(1L, "todo");

        furnitureRepository.save(furniture);
        log.info("{}", taskRepository.findByIdAndMemberId(task.getId(), task.getMemberId()));
    }
}
