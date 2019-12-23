package com.depromeet.todo.presentation.weather;

import com.depromeet.todo.application.Displayable;
import lombok.Data;

@Data
public class WeatherResponse implements Displayable {
    private String precipitationType;
}
