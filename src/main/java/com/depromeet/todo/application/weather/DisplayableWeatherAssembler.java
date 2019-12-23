package com.depromeet.todo.application.weather;

import com.depromeet.todo.application.Displayable;
import com.depromeet.todo.domain.weather.Weather;

public interface DisplayableWeatherAssembler {
    Displayable toDisplayableWeather(Weather weather);
}
