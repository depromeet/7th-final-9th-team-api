package com.depromeet.todo.domain.room;

import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

public class RoomCreatedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 662905892387394085L;

    private final Long roomId;

    private RoomCreatedEvent(Object source, Long roomId) {
        super(source);
        Assert.notNull(roomId, "'roomId' must not be null");
        this.roomId = roomId;
    }

    public static RoomCreatedEvent of(Object source, Long roomId) {
        return new RoomCreatedEvent(source, roomId);
    }

    public Long getRoomId() {
        return roomId;
    }
}
