package com.depromeet.todo.infrastructure.kma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KmaApiBody<T extends KmaApiWeatherItem> {
    @JsonProperty("items")
    private KmaApiBodyItems<T> items;
    @JsonProperty("numOfRows")
    private Long numberOfRows;
    @JsonProperty("pageNo")
    private Long pageNumber;
    @JsonProperty("totalCount")
    private Long totalCount;
}
