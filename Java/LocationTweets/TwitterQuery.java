/**
 * Data object for twitter query parameters.
 * It holds all the necessary values to make the
 * twitter search. The location is stored for caching.
 * When the data is read from the cache, the
 * main location should also be stored here.
 * If the geographical data is missing, the main location
 * (or the getLocation() value) is used to make the
 * query from twitter API.
 */
class TwitterQuery implements ITwitterQuery {

    /**
     * Point's latitude.
     */
    private double latitude;

    /**
     * Point's longitude.
     */
    private double longitude;

    /**
     * Point's radius.
     */
    private double radius;

    /**
     * Location official name.
     */
    private String location;

    /**
     * Queried tweets count.
     */
    private int count;

    @Override
    public void setLatitude(double locLatitude) {
        latitude = locLatitude;
    }

    @Override
    public void setLongitude(double locLongitude) {
        longitude = locLongitude;
    }

    @Override
    public void setRadius(double locRadius) {
        radius = locRadius;
    }

    @Override
    public void setLocation(String locationName) {
        location = locationName;
    }

    /**
     * The count of tweets to query.
     *
     * @param tweetsCount Count of tweets to query
     */
    @Override
    public void setCount(int tweetsCount) {
        count = tweetsCount;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public int getCount() {
        return count;
    }

    /**
     * Checks whether the given instance has necessary parameters set. This is
     * used in case of caching - if location exist but all the parameters do not
     * exist, the cache needs to be updated.
     *
     * @return Whether latitude, longitude and radius are set
     */
    @Override
    public boolean isGeoSet() {
        return latitude != 0 && longitude != 0 && radius > 0;
    }
}
