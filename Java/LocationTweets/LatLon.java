class LatLon {

    // Most formulas and information you will find here
    // is taken from http://www.movable-type.co.uk/scripts/latlong.html
    // EN.

    public double latitude;
    public double longitude;

    //    (Mean) radius of earth in kilometres.
    public static final int RADIUS = 6371;

    LatLon() {}

    /**
     * Creates a LatLon point on the earth's surface at the specified latitude / longitude.
     *
     * @param latitude {double} - Latitude in degrees.
     * @param longitude {double} - Longitude in degrees.
     */
    LatLon(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

        double φ1 = StrictMath.toRadians(p1.latitude),
                λ1 = StrictMath.toRadians(p1.longitude);
        double φ2 = StrictMath.toRadians(p2.latitude),
                λ2 = StrictMath.toRadians(p2.longitude);
        double Δφ = φ2 - φ1;
        double Δλ = λ2 - λ1;

        double a = StrictMath.sin(Δφ/2) * StrictMath.sin(Δφ/2) +
                StrictMath.cos(φ1) * StrictMath.cos(φ2) *
                        StrictMath.sin(Δλ/2) * StrictMath.sin(Δλ/2);
        double c = 2 * StrictMath.atan2(Math.sqrt(a), StrictMath.sqrt(1-a));

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

        double Δλ = Math.toRadians(p2.longitude - p1.longitude);

        //convert to radians
        double φ1 = Math.toRadians(p1.latitude);
        double λ1 = Math.toRadians(p1.longitude);
        double φ2 = Math.toRadians(p2.latitude);

        double Bx = Math.cos(φ2) * Math.cos(Δλ);
        double By = Math.cos(φ2) * Math.sin(Δλ);
        double φ3 = Math.atan2(Math.sin(φ1) + Math.sin(φ2), Math.sqrt((Math.cos(φ1) + Bx) * (Math.cos(φ1) + Bx) + By * By));
        double λ3 = λ1 + Math.atan2(By, Math.cos(φ1) + Bx);

        return new LatLon(StrictMath.toDegrees(φ3), StrictMath.toDegrees(λ3));
    }
}