package com.depromeet.todo.application.room;

import com.depromeet.todo.application.Displayable;
import com.depromeet.todo.domain.room.Room;

public interface DisplayableRoomAssembler {
    Displayable toDisplayableMember(Room room);
}
