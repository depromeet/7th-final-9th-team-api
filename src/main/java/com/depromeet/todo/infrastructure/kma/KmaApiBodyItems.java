package com.depromeet.todo.infrastructure.kma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class KmaApiBodyItems<T extends KmaApiWeatherItem> {
    @JsonProperty("item")
    private List<T> itemList;
}
