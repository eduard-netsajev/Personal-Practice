/**
 * Data object for twitter query parameters.
 * It holds all the necessary values to make the
 * twitter search. The location is stored for caching.
 * When the data is read from the cache, the
 * main location should also be stored here.
 * If the geographical data is missing, the main location
 * (or the getLocation() value) is used to make the
 * query from twitter API.
 * @author Ago
 *
 */
public class TwitterQuery implements ITwitterQuery {

    private double latitude;
    private double longitude;
    private double radius;
    private String location;
    private int count;

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The count of tweets to query.
     *
     * @param count Count of tweets to query
     */
    @Override
    public void setCount(int count) {
        this.count = count;
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
