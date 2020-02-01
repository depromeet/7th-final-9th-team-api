package com.depromeet.todo.domain.room;

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
    private RoomFactory roomFactory;

    @Test
    void room_생성() {
        Room room = roomFactory.createRoom(1L, RoomType.BEDROOM);
        assertThat(room).isNotNull();
        assertThat(room.getMemberId()).isEqualTo(1L);
        assertThat(room.getType()).isEqualTo(RoomType.BEDROOM);
    }
}