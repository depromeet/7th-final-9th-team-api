package com.depromeet.todo.domain.room;

import com.depromeet.todo.domain.IdGenerator;
import com.depromeet.todo.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RoomTest {
    @Autowired
    private IdGenerator idGenerator;

    @Test
    void room_생성() {
        Room room = Room.of(idGenerator, mock(Member.class), RoomType.BEDROOM);
        assertThat(room).isNotNull();
        assertThat(room.getType()).isEqualTo(RoomType.BEDROOM);
    }
}