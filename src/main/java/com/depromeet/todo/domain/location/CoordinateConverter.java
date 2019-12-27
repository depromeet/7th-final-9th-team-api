package com.depromeet.todo.domain.location;

import org.springframework.util.Assert;

public class CoordinateConverter {
    private static final double DEGRAD = Math.PI / 180.0;
    private static final double RADDEG = 180.0 / Math.PI;

    private static final double RADIUS_OF_EARTH = 6371.00877; // 지구 반경(km)
    private static final double LENGTH_OF_GRID = 5.0; // 격자 간격(km)
    private static final double SLAT1 = 30.0; // 투영 위도1(degree)
    private static final double SLAT2 = 60.0; // 투영 위도2(degree)
    private static final double ORIGIN_LONGITUDE = 126.0; // 기준점 경도(degree)
    private static final double ORIGIN_LATITUDE = 38.0; // 기준점 위도(degree)
    private static final double ORIGIN_X = 43; // 기준점 X좌표(GRID)
    private static final double ORIGIN_Y = 136; // 기1준점 Y좌표(GRID)

    private static final double NUMBER_OF_GRID = RADIUS_OF_EARTH / LENGTH_OF_GRID;
    private static final double slat1 = SLAT1 * DEGRAD;
    private static final double slat2 = SLAT2 * DEGRAD;
    private static final double olon = ORIGIN_LONGITUDE * DEGRAD;
    private static final double olat = ORIGIN_LATITUDE * DEGRAD;

    private static final double sn;
    private static final double sf;
    private static final double ro;

    static {
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(
                Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        );
        sf = Math.pow(Math.tan(Math.PI * 0.25 + slat1 * 0.5), sn) * Math.cos(slat1) / sn;
        ro = NUMBER_OF_GRID * sf / Math.pow(Math.tan(Math.PI * 0.25 + olat * 0.5), sn);
    }

    private CoordinateConverter() {
        // Noninstantiable utility class
    }

    public static PcsLocation toPcsLocation(GcsLocation gcsLocation) {
        Assert.notNull(gcsLocation, "'lcsLocation' must not be null");

        double latitude = gcsLocation.getLatitude();
        double longitude = gcsLocation.getLongitude();

        double ra = Math.tan(Math.PI * 0.25 + (latitude) * DEGRAD * 0.5);
        ra = NUMBER_OF_GRID * sf / Math.pow(ra, sn);
        double theta = longitude * DEGRAD - olon;
        if (theta > Math.PI) {
            theta -= 2.0 * Math.PI;
        } else if (theta < -Math.PI) {
            theta += 2.0 * Math.PI;
        }
        theta *= sn;
        return PcsLocation.of(
                (int) Math.floor(ra * Math.sin(theta) + ORIGIN_X + 0.5),
                (int) Math.floor(ro - ra * Math.cos(theta) + ORIGIN_Y + 0.5)
        );
    }
}
