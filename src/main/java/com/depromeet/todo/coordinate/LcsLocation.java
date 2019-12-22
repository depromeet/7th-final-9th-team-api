package com.depromeet.todo.coordinate;

import lombok.Value;

/**
 * 지리좌표계 (Geographic Coordinate System, GCS)
 */
@Value(staticConstructor = "of")
public class LcsLocation {
    public final Double latitude;
    public final Double longitude;
}
