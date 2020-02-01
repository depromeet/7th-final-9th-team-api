package com.depromeet.todo.domain.room;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum RoomType {
    LIVING_ROOM("living-room"),
    BEDROOM("bedroom"),
    KITCHEN("kitchen"),
    BATHROOM("bathroom"),
    UNKNOWN("");

    public static final Set<RoomType> AVAILABLE_TYPES = Arrays.stream(RoomType.values())
            .filter(it -> it.equals(UNKNOWN))
            .collect(Collectors.toSet());

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
