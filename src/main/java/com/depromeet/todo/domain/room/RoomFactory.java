package com.depromeet.todo.domain.room;

import com.depromeet.todo.domain.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomFactory {
    private final RoomRepository roomRepository;
    private final IdGenerator snowFlakeIdGenerator;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<Room> createInitialRooms(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");

        Set<RoomType> existTypes = roomRepository.findByMemberId(memberId).stream()
                .map(Room::getType)
                .collect(Collectors.toSet());

        return RoomType.AVAILABLE_TYPES.stream()
                .filter(it -> !existTypes.contains(it))
                .map(it -> this.createRoom(memberId, it))
                .collect(Collectors.toList());
    }

    public Room createRoom(Long memberId, RoomType roomType) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(roomType, "'roomType' must not be null");

        Room room = new Room(
                snowFlakeIdGenerator.generate(),
                memberId,
                roomType
        );
        roomRepository.save(room);

        applicationEventPublisher.publishEvent(
                RoomCreatedEvent.of(this, room.getRoomId())
        );
        return room;
    }
}
