package com.depromeet.todo.domain.builder;

import com.depromeet.todo.domain.HouseBuilder;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test-member")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class HouseBuilderTest {

    private static final Logger log = LoggerFactory.getLogger(HouseBuilder.class);

    @Autowired
    private HouseBuilder houseBuilder;
    @Autowired
    private RoomRepository roomRepository;

    @DisplayName("해당 계정에 기본으로 제공하는 집을 짓는다")
    @Test
    void build() {
        List<Room> rooms = houseBuilder.build(1L);
        log.info("Furniture : {}", rooms.get(0).getFurniture());


        Optional<Room> temp = roomRepository.findById(rooms.get(0)
                                                           .getRoomId());
        assertThat(temp.get().getFurniture()).hasSize(5);
    }
}