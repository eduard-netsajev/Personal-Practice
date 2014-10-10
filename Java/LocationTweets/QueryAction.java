/**
 * Data object to hold parameters for query action.
 */
class QueryAction implements IQueryAction {

    /**
     * Official name of the location.
     */
    private String location;

    /**
     * Amount of tweets to fetch.
     */
    private int count;

    @Override
    public void setLocation(String newLocation) {
        location = newLocation;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setCount(int newCount) {
        count = newCount;
    }

    @Override
    public int getCount() {
        return count;
    }
}