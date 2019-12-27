package com.depromeet.todo.infrastructure.kma;

import java.util.stream.Stream;

public enum KmaPrecipitationType {
    KMA_NONE(0),   // 없음
    KMA_RAIN(1),   // 비
    KMA_SLEET(2),  // 진눈깨비
    KMA_SNOW(3),   // 눈
    KMA_SHOWER(4), // 소나기
    KMA_UNKNOWN(-1);

    private final int value;

    KmaPrecipitationType(int value) {
        this.value = value;
    }

    public static KmaPrecipitationType from(Integer value) {
        if (value == null) {
            return KMA_UNKNOWN;
        }
        return Stream.of(KmaPrecipitationType.values())
                .filter(status -> value.equals(status.value))
                .findFirst()
                .orElse(KmaPrecipitationType.KMA_UNKNOWN);
    }
}
