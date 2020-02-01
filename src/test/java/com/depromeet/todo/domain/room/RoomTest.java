package com.depromeet.todo.domain.room;

import com.depromeet.todo.domain.IdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RoomTest {
    @Autowired
    private IdGenerator idGenerator;

    @Test
    void room_생성() {
        Room room = Room.of(idGenerator, 1L, RoomType.BEDROOM);
        assertThat(room).isNotNull();
        assertThat(room.getMemberId()).isEqualTo(1L);
        assertThat(room.getType()).isEqualTo(RoomType.BEDROOM);
    }
}