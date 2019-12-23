package com.depromeet.todo.domain.location;

/**
 * 위치
 */
public interface Location {
    static Location of(Double latitude, Double longitude) {
        return GcsLocation.of(latitude, longitude);
    }

    PcsLocation toPcsLocation();
}
