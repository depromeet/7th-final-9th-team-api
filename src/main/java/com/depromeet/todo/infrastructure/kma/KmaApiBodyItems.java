package com.depromeet.todo.infrastructure.kma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class KmaApiBodyItems<T extends KmaApiWeatherItem> {
    @JsonProperty("item")
    private List<T> itemList;

    @SuppressWarnings("squid:S1172")    // XXX: items: "" 로 들어오는 경우에 대한 방어코드
    public KmaApiBodyItems(String input) {
        this.itemList = Collections.emptyList();
    }
}
