package com.depromeet.todo.domain.room;

import java.util.Arrays;

public enum RoomType {
    LIVING_ROOM("living-room"),
    BEDROOM("bedroom"),
    KITCHEN("kitchen"),
    BATHROOM("bathroom"),
    UNKNOWN("");

    private final String name;

    RoomType(String name) {
        this.name = name;
    }

    public static RoomType fromName(String name) {
        if (name == null) {
            return UNKNOWN;
        }
        return Arrays.stream(RoomType.values())
                .filter(it -> it.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public String getName() {
        return name;
    }
}
