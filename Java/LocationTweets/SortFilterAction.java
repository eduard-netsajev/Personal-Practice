class SortFilterAction implements IFilterAction {

    private int sortField;
    private int sortOrder = 1;
    private String searchKeyword;

    @Override
    public int getSortField() {
        return sortField;
    }

    /**
     * Sets sort field. Use IFilterAction.FIELD_* constants.
     *
     * @param field Sort field, use IFilterAction.FIELD_* constants.
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
     * Sets sort order. Use IFilterAction.ORDER_* constants.
     *
     * @param order Sort order, use IFilterAction.ORDER_* constants.
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