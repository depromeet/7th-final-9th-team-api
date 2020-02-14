package com.depromeet.todo.domain;

import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.furniture.FurnitureType;
import com.depromeet.todo.domain.member.MemberCreatedEvent;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomFactory;
import com.depromeet.todo.domain.room.RoomType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.depromeet.todo.domain.furniture.FurnitureType.*;
import static com.depromeet.todo.domain.room.RoomType.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class HouseFactory {

    private final RoomFactory roomFactory;

    @Transactional
    @EventListener
    public List<Room> build(MemberCreatedEvent memberCreatedEvent) {
        Assert.notNull(memberCreatedEvent, "'memberCreatedEvent' must not be null");
        Long memberId = memberCreatedEvent.getMemberId();
        return Arrays.stream(BasicItem.values())
                                   .map(item -> {
                                       List<Furniture> furnitures = makeFurniture(memberId, item);
                                       return roomFactory.createRoom(memberId, item.roomType, furnitures);
                                   })
                                   .collect(Collectors.toList());
    }

    private List<Furniture> makeFurniture(Long memberId, BasicItem item) {
        return Arrays.stream(item.getFurnitureTypes())
                     .map(type -> Furniture.of(memberId, type))
                     .collect(Collectors.toList());
    }

    enum BasicItem {
        OPTION1(BEDROOM, BED, CLOSET, DESK, WASTE_BIN, DRESSING_TABLE),
        OPTION2(LIVING_ROOM),
        OPTION3(KITCHEN),
        OPTION4(BATHROOM);

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
