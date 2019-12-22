package com.depromeet.todo.coordinate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CoordinateConverterTest {

    @Test
    void toPcsLocation() {
        this.testToPcsLocation(
                37.579871128849334,
                126.98935225645432,
                60,
                127
        );
    }

    private void testToPcsLocation(double latitude, double longitude, int x, int y) {
        // given
        LcsLocation lcsLocation = LcsLocation.of(latitude, longitude);
        // when
        PcsLocation pcsLocation = CoordinateConverter.toPcsLocation(lcsLocation);
        // then 1
        assertThat(pcsLocation.getX()).isEqualTo(x);
        assertThat(pcsLocation.getY()).isEqualTo(y);
        // then 2
        LegacyCoordinateConverter.LatXLngY result =
                LegacyCoordinateConverter.convertGRID_GPS(LegacyCoordinateConverter.TO_GRID, latitude, longitude);
        assertThat(pcsLocation.getX()).isEqualTo((int) result.x);
        assertThat(pcsLocation.getY()).isEqualTo((int) result.y);
    }
}