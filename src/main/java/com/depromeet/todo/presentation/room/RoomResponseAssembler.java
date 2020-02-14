package com.depromeet.todo.presentation.room;

import com.depromeet.todo.application.room.DisplayableRoomAssembler;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.presentation.furniture.FurnitureResponseAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomResponseAssembler implements DisplayableRoomAssembler {

    private final FurnitureResponseAssembler furnitureResponseAssembler;

    @Override
    public RoomResponse toDisplayableRoom(Room room) {
        if (room == null) {
            return null;
        }
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setId(room.getId());
        roomResponse.setType(room.getType().getName());
        roomResponse.setFurnitures(furnitureResponseAssembler.toDisplayableFurniture(room.getFurniture()));
        roomResponse.setCreatedAt(room.getCreatedAt());
        roomResponse.setUpdatedAt(room.getUpdatedAt());
        return roomResponse;
    }
}
