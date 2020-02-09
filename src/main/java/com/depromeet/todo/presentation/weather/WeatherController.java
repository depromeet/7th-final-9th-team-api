package com.depromeet.todo.presentation.weather;

import com.depromeet.todo.domain.location.Location;
import com.depromeet.todo.domain.weather.Weather;
import com.depromeet.todo.domain.weather.WeatherService;
import com.depromeet.todo.presentation.common.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final WeatherResponseAssembler weatherResponseAssembler;

    @ApiOperation(value = "precipitationType: {\"NONE\" | \"SNOW\" | \"RAIN\"}")
    @GetMapping("/weathers")
    public ApiResponse<WeatherResponse> getWeather(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) {
        Weather weather = weatherService.getCurrentWeather(
                Location.of(latitude, longitude),
                LocalDateTime.now()
        );
        WeatherResponse weatherResponse = weatherResponseAssembler.toDisplayableWeather(weather);
        return ApiResponse.successFrom(weatherResponse);
    }
}
