package com.depromeet.todo.coordinate;

import lombok.Value;

/**
 * 투영좌표계 (Projected Coordinate System, PCS)
 */
@Value(staticConstructor = "of")
public class PcsLocation {
    public final Integer x;
    public final Integer y;
}
