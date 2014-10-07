/**
 * Data object to hold parameters for query action.
 * The methods are regular getters/setters.
 * @author Ago
 *
 */
class QueryAction implements IQueryAction {

    private String location;
    private int count;

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }
}
