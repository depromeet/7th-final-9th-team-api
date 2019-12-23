package com.depromeet.todo.domain.location;

import lombok.Value;

/**
 * 지리좌표계 (Geographic Coordinate System, GCS)
 */
@Value(staticConstructor = "of")
public class GcsLocation implements Location {
    public final Double latitude;
    public final Double longitude;

    @Override
    public PcsLocation toPcsLocation() {
        return CoordinateConverter.toPcsLocation(this);
    }
}
