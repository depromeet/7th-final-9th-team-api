package com.depromeet.todo.application.weather;

import com.depromeet.todo.domain.weather.Weather;

public interface WeatherAssembler<T extends WeatherSupport> {
    Weather toWeather(T weatherSupport);
}
