package com.depromeet.todo.infrastructure.kma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrentKmaApiWeatherItem implements KmaApiWeatherItem {
    private String baseDate; // 20191222
    private String baseTime; // 1900
    private String category; // REH
    private String nx; // 60
    private String ny; // 125
    @JsonProperty("obsrValue")
    private String observedValue; // 84

    @Override
    public boolean isPrecipitation() {
        if (category == null) {
            return false;
        }
        return category.equalsIgnoreCase("PTY");
    }

    @Override
    public Integer intValue() {
        if (observedValue == null) {
            throw new IllegalStateException("'observedValue' must not be null");
        }
        return Integer.parseInt(observedValue);
    }
}
