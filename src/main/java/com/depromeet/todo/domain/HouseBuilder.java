package com.depromeet.todo.domain;

import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.furniture.FurnitureType;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomFactory;
import com.depromeet.todo.domain.room.RoomType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.depromeet.todo.domain.furniture.FurnitureType.*;
import static com.depromeet.todo.domain.room.RoomType.BEDROOM;

@Slf4j
@Component
@RequiredArgsConstructor
public class HouseBuilder {

    private final RoomFactory roomFactory;

    public List<Room> build(Long memberId) {
        return Arrays.stream(BasicItem.values())
                                   .map(item -> {
                                       List<Furniture> furnitures = makeFurniture(memberId, item);
                                       Room room = roomFactory.createRoom(memberId, item.roomType, furnitures);
                                       return room;
                                   })
                                   .collect(Collectors.toList());
    }

    private List<Furniture> makeFurniture(Long memberId, BasicItem item) {
        return Arrays.stream(item.getFurnitureTypes())
                     .map(type -> Furniture.of(memberId, type))
                     .collect(Collectors.toList());
    }

    enum BasicItem {
        DEFAULT_ROOM(BEDROOM, BED, CLOSET, DESK, WASTE_BIN, DRESSING_TABLE);

        private RoomType roomType;
        private FurnitureType[] furnitureTypes;

        BasicItem(RoomType roomType, FurnitureType... furnitureTypes) {
            this.roomType = roomType;
            this.furnitureTypes = furnitureTypes;
        }

        public FurnitureType[] getFurnitureTypes() {
            return furnitureTypes;
        }
    }
}
