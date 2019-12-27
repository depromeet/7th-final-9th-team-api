package com.depromeet.todo.infrastructure.kma;

import com.depromeet.todo.application.weather.WeatherAssembler;
import com.depromeet.todo.domain.location.Location;
import com.depromeet.todo.domain.weather.Weather;
import com.depromeet.todo.domain.weather.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Slf4j
@Service
public class KmaWeatherService implements WeatherService {
    private static final ParameterizedTypeReference<KmaApiResult<CurrentKmaApiWeatherItem>> PTR_KMA_API_RESPONSE_CURRENT_WEATHER_ITEM;

    static {
        PTR_KMA_API_RESPONSE_CURRENT_WEATHER_ITEM = new ParameterizedTypeReference<KmaApiResult<CurrentKmaApiWeatherItem>>() {
        };
    }

    private final RestTemplate kmaRestTemplate;
    private final String kmaHost;
    private final String currentWeatherPath;
    private final WeatherAssembler<KmaApiResult<CurrentKmaApiWeatherItem>> kmaWeatherAssembler;

    public KmaWeatherService(@Qualifier("kmaRestTemplate") RestTemplate kmaRestTemplate,
                             @Value("${weather.kma.host}") String kmaHost,
                             @Value("${weather.kma.path.current}") String currentWeatherPath,
                             @Qualifier("kmaWeatherAssembler") WeatherAssembler<KmaApiResult<CurrentKmaApiWeatherItem>> kmaWeatherAssembler) {
        this.kmaRestTemplate = kmaRestTemplate;
        this.kmaHost = kmaHost;
        this.currentWeatherPath = currentWeatherPath;
        this.kmaWeatherAssembler = kmaWeatherAssembler;
    }

    @Override
    public Weather getCurrentWeather(Location location, LocalDateTime now) {
        URI requestUrl = UriComponentsBuilder.fromHttpUrl(kmaHost)
                .path(currentWeatherPath)
                .queryParams(new KmaWeatherQueryParamBuilder()
                        .localDateTime(now)
                        .location(location)
                        .build())
                .build(true)
                .toUri();
        ResponseEntity<KmaApiResult<CurrentKmaApiWeatherItem>> responseEntity = kmaRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl),
                PTR_KMA_API_RESPONSE_CURRENT_WEATHER_ITEM
        );

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to get current weather. statusCode:" + responseEntity.getStatusCode());
            throw new KmaWeatherException("Failed to get current weather. statusCode:" + responseEntity.getStatusCode());
        }

        return kmaWeatherAssembler.toWeather(responseEntity.getBody());
    }

    public static class KmaWeatherQueryParamBuilder {
        private static final int SIZE_DEFAULT = 10;
        private static final int PAGE_DEFAULT = 1;

        private LocalDateTime localDateTime;
        private Location location;
        private int size = SIZE_DEFAULT;
        private int page = PAGE_DEFAULT;

        public KmaWeatherQueryParamBuilder localDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
            return this;
        }

        public KmaWeatherQueryParamBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public KmaWeatherQueryParamBuilder size(int size) {
            this.size = size;
            return this;
        }

        public KmaWeatherQueryParamBuilder page(int page) {
            this.page = page;
            return this;
        }

        public MultiValueMap<String, String> build() {
            Assert.notNull(localDateTime, "'localDateTime' must not be null");
            Assert.notNull(location, "'location' must not be null");

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.put("base_date", Collections.singletonList(localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
            multiValueMap.put("base_time", Collections.singletonList(localDateTime.format(DateTimeFormatter.ofPattern("HH00"))));
            multiValueMap.put("nx", Collections.singletonList(String.valueOf(location.toPcsLocation().getX())));
            multiValueMap.put("ny", Collections.singletonList(String.valueOf(location.toPcsLocation().getY())));
            multiValueMap.put("numOfRows", Collections.singletonList(String.valueOf(size)));
            multiValueMap.put("pageNo", Collections.singletonList(String.valueOf(page)));
            multiValueMap.put("_type", Collections.singletonList("json"));
            return multiValueMap;
        }
    }
}
