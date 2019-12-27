package com.depromeet.todo.domain.weather;

import lombok.Value;

/**
 * 날씨
 */
@Value(staticConstructor = "of")
public class Weather {
    private final PrecipitationType precipitationType;
}