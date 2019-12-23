package com.depromeet.todo.integration;

import com.depromeet.todo.domain.location.GcsLocation;
import com.depromeet.todo.domain.location.PcsLocation;
import com.depromeet.todo.domain.weather.Weather;
import com.depromeet.todo.domain.weather.WeatherService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore // 직접 기상청 api 를 호출하는 테스트이므로, 평소에는 ignore 해둡니다
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class KmaWeatherServiceIntegrationTest {
    @Autowired
    private WeatherService KmaWeatherService;

    @Test
    void 초단기실황조회_pcs_좌표() {
        Weather weather = KmaWeatherService.getCurrentWeather(
                PcsLocation.of(63, 127),
                LocalDateTime.now()
        );
        assertThat(weather).isNotNull();
    }

    @Test
    void 초단기실황조회_gcs_좌표() {
        Weather weather = KmaWeatherService.getCurrentWeather(
                GcsLocation.of(37.4876095, 126.9833778),
                LocalDateTime.now()
        );
        assertThat(weather).isNotNull();
    }
}
