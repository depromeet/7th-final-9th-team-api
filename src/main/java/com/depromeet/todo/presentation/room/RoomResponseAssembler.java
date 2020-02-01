package com.depromeet.todo.presentation.room;

import com.depromeet.todo.application.room.DisplayableRoomAssembler;
import com.depromeet.todo.domain.room.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomResponseAssembler implements DisplayableRoomAssembler {

    @Override
    public RoomResponse toDisplayableMember(Room room) {
        if (room == null) {
            return null;
        }
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setId(room.getRoomId());
        roomResponse.setType(room.getType().name());
        roomResponse.setCreatedAt(room.getCreatedAt());
        roomResponse.setUpdatedAt(room.getUpdatedAt());
        return roomResponse;
    }
}
