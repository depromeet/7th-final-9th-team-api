package com.depromeet.todo.presentation.weather;

import com.depromeet.todo.application.weather.DisplayableWeatherAssembler;
import com.depromeet.todo.domain.weather.PrecipitationType;
import com.depromeet.todo.domain.weather.Weather;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WeatherResponseAssembler implements DisplayableWeatherAssembler {
    @Override
    public WeatherResponse toDisplayableWeather(Weather weather) {
        if (weather == null) {
            return null;
        }
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setPrecipitationType(
                Optional.ofNullable(weather.getPrecipitationType())
                        .map(Enum::name)
                        .orElse(PrecipitationType.UNKNOWN.name())
        );
        return weatherResponse;
    }
}
