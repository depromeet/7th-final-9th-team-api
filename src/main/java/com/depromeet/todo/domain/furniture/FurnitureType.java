package com.depromeet.todo.domain.furniture;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum FurnitureType {
    BED("bed"),
    CLOSET("closet"),
    DESK("desk"),
    WASTE_BIN("waste-bin"),
    DRESSING_TABLE("dressing-table"),

    REFRIGERATOR("refrigerator"),
    TABLE("table"),
    GAS_STOVE("gas-stove"),
    KITCHEN_SINK("kitchen-sink"),
    KITCHEN_WASTE_BIN("kitchen-waste-bin"),

    TOILET("toilet"),
    SHOWER_BOOTH("shower-booth"),
    WASHING_MACHINE("washing-machine"),
    BATHROOM_WASTE_BIN("bathroom-waste-bin"),
    BATHROOM_SINK("bathroom-sink"),
    UNKNOWN("");

    public static final Set<FurnitureType> AVAILABLE_SET = Arrays.stream(FurnitureType.values())
                                                                 .filter(it -> !it.equals(UNKNOWN))
                                                                 .collect(Collectors.toSet());
    private final String name;

    FurnitureType(String name) {
        this.name = name;
    }

    public static FurnitureType fromName(String name) {
        if (StringUtils.isEmpty(name)) {
            return UNKNOWN;
        }
        return Arrays.stream(FurnitureType.values())
                .filter(it -> it.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public String getName() {
        return name;
    }
}
