class SetCountAction implements IAction {

    private int count;

    SetCountAction(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}