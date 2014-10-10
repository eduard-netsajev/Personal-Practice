/**
 * Data object to hold information
 * about particular geographic point.
 * Also contains several operations
 * as to get the distance or midpoint
 * between two such points.
 */
class LatLon {

    // Most formulas and information you will find here
    // is taken from http://www.movable-type.co.uk/scripts/latlong.html
    // EN.

    /**
     * Latitude of the point.
     */
    public double latitude;

    /**
     * Longitude of the point.
     */
    public double longitude;

    /**
     * Mean radius of earth in kilometres.
     */
    public static final int RADIUS = 6371;

    /**
     * Creates a LatLon point on the earth's
     * surface at the specified latitude / longitude.
     *
     * @param pointLatitude {double} - Latitude in degrees.
     * @param pointLongitude {double} - Longitude in degrees.
     */
    LatLon(double pointLatitude, double pointLongitude) {
        latitude = pointLatitude;
        longitude = pointLongitude;
    }

    /**
     * Returns the distance from one point to another (using haversine formula).
     *
     * @param p1 {LatLon} Latitude/longitude of the first point.
     * @param p2 {LatLon} Latitude/longitude of the second point.
     * @return {number} Distance between this point and destination point,
     * in km (on sphere of Earth radius).
     */
    public static double distance(LatLon p1, LatLon p2) {

        double lat1 = StrictMath.toRadians(p1.latitude),
                lon1 = StrictMath.toRadians(p1.longitude);
        double lat2 = StrictMath.toRadians(p2.latitude),
                lon2 = StrictMath.toRadians(p2.longitude);
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = StrictMath.sin(dlat / 2) * StrictMath.sin(dlat / 2)
                + StrictMath.cos(lat1) * StrictMath.cos(lat2)
                * StrictMath.sin(dlon / 2) * StrictMath.sin(dlon / 2);
        double c = 2 * StrictMath.atan2(Math.sqrt(a), StrictMath.sqrt(1 - a));

        return RADIUS * c;
    }

    /**
     * Returns the midpoint between two points.
     *
     * @param p1 {LatLon} - Latitude/longitude of the first point.
     * @param p2 {LatLon} - Latitude/longitude of the second point.
     * @return {LatLon} Midpoint between these two points.
     */
    public static LatLon midpoint(LatLon p1, LatLon p2) {

        double dlon = Math.toRadians(p2.longitude - p1.longitude);

        //convert to radians
        double lat1 = Math.toRadians(p1.latitude);
        double lon1 = Math.toRadians(p1.longitude);
        double lat2 = Math.toRadians(p2.latitude);

        double bx = Math.cos(lat2) * Math.cos(dlon);
        double by = Math.cos(lat2) * Math.sin(dlon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2),
                Math.sqrt((Math.cos(lat1) + bx) * (Math.cos(lat1) + bx)
                        + by * by));
        double lon3 = lon1 + Math.atan2(by, Math.cos(lat1) + bx);

        return new LatLon(StrictMath.toDegrees(lat3),
                StrictMath.toDegrees(lon3));
    }
}