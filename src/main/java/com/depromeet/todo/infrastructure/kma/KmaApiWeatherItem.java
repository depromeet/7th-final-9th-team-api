package com.depromeet.todo.infrastructure.kma;

public interface KmaApiWeatherItem {
    default boolean isPrecipitation() {
        return false;
    }

    Integer intValue();
}
