package com.depromeet.todo.domain.weather;

import com.depromeet.todo.domain.location.Location;

import java.time.LocalDateTime;

public interface WeatherService {

    /**
     * 특정 지역의 현재 날씨를 조회합니다.
     *
     * @param location 현재 위치
     * @param now      현재 시각
     * @return 현재 날씨
     */
    Weather getCurrentWeather(Location location, LocalDateTime now);
}
