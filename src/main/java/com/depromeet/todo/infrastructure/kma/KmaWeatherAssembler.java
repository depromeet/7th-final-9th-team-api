package com.depromeet.todo.infrastructure.kma;

import com.depromeet.todo.application.weather.WeatherAssembler;
import com.depromeet.todo.domain.weather.PrecipitationType;
import com.depromeet.todo.domain.weather.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KmaWeatherAssembler implements WeatherAssembler<KmaApiResult<CurrentKmaApiWeatherItem>> {
    private final ObjectMapper objectMapper;

    @Override
    public Weather toWeather(KmaApiResult<CurrentKmaApiWeatherItem> kmaApiResult) {
        PrecipitationType precipitationType = Optional.ofNullable(kmaApiResult)
                .map(KmaApiResult::getKmaApiResponse)
                .map(KmaApiResponse::getBody)
                .map(KmaApiBody::getItems)
                .map(KmaApiBodyItems::getItemList)
                .map(it -> it.stream()
                        .filter(KmaApiWeatherItem::isPrecipitation)
                        .map(KmaApiWeatherItem::intValue)
                        .findFirst()
                        .orElseThrow(() -> {
                            log.error("Failed to parse kmaApiResult. Category 'PTY' is not found");
                            return new IllegalArgumentException("Failed to parse kmaApiResult. Category 'PTY' is not found");
                        }))
                .map(KmaPrecipitationType::from)
                .map(this::toPrecipitationType)
                .orElseThrow(() -> {
                    log.error("Failed to parse kmaApiResult. kmaApiResult: {}", kmaApiResult);
                    return new IllegalArgumentException("Failed to parse kmaApiResult");
                });
        return Weather.of(precipitationType);
    }

    private PrecipitationType toPrecipitationType(KmaPrecipitationType kmaPrecipitationType) {
        if (kmaPrecipitationType == null) {
            return PrecipitationType.UNKNOWN;
        }
        switch (kmaPrecipitationType) {
            case KMA_NONE:
                return PrecipitationType.NONE;
            case KMA_RAIN:
            case KMA_SLEET:
            case KMA_SHOWER:
                return PrecipitationType.RAIN;
            case KMA_SNOW:
                return PrecipitationType.SNOW;
            case KMA_UNKNOWN:
                return PrecipitationType.UNKNOWN;
            default:
                throw new IllegalArgumentException("It's not supported type. type:" + this);
        }
    }

}
