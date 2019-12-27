package com.depromeet.todo.infrastructure.kma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrentKmaApiWeatherItem implements KmaApiWeatherItem {
    private static final String CATEGORY_NAME_OF_PRECIPITATION_TYPE = "PTY";

    private String baseDate; // 20191222
    private String baseTime; // 1900
    private String category; // REH
    private String nx; // 60
    private String ny; // 125
    @JsonProperty("obsrValue")
    private String observedValue; // 84

    @Override
    public boolean isPrecipitation() {
        return CATEGORY_NAME_OF_PRECIPITATION_TYPE.equalsIgnoreCase(category);
    }

    @Override
    public Integer intValue() {
        try {
            return Integer.parseInt(observedValue);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Failed to parse observed value. observedValue:" + observedValue);
        }
    }
}
