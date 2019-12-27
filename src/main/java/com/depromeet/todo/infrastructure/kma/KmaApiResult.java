package com.depromeet.todo.infrastructure.kma;

import com.depromeet.todo.application.weather.WeatherSupport;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class KmaApiResult<T extends KmaApiWeatherItem> implements WeatherSupport {
    @JsonProperty("response")
    private KmaApiResponse<T> kmaApiResponse;
}
