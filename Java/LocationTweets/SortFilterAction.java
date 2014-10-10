/**
 * Data object for sort and search actions, holds action parameters.
 */
class SortFilterAction implements IFilterAction {

    /**
     * Which field use to sort.
     */
    private int sortField;

    /**
     * Sort order.
     */
    private int sortOrder = 1;

    /**
     * Search keyword.
     */
    private String searchKeyword;

    @Override
    public int getSortField() {
        return sortField;
    }

    /**
     * Sets sort field.
     *
     * @param field Sort field.
     */
    @Override
    public void setSortField(int field) {
        sortField = field;
    }

    @Override
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets sort order.
     *
     * @param order Sort order.
     */
    @Override
    public void setSortOrder(int order) {
        sortOrder = order;
    }

    @Override
    public String getSearchKeyword() {
        return searchKeyword;
    }

    @Override
    public void setSearchKeyword(String keyword) {
        searchKeyword = keyword;
    }
}