package com.depromeet.todo.infrastructure.kma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KmaApiResponse<T extends KmaApiWeatherItem> {
    @JsonProperty("header")
    private KmaApiHeader header;
    @JsonProperty("body")
    private KmaApiBody<T> body;
}
