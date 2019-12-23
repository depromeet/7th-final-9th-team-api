package com.depromeet.todo.domain.location;

import lombok.Value;

/**
 * 투영좌표계 (Projected Coordinate System, PCS)
 */
@Value(staticConstructor = "of")
public class PcsLocation implements Location {
    public final Integer x;
    public final Integer y;

    @Override
    public PcsLocation toPcsLocation() {
        return this;
    }
}
