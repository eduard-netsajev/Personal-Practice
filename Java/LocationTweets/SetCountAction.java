/**
 *  Data object that hold new count
 *  to set default count to.
 */
class SetCountAction implements IAction {

    /**
     * Count.
     */
    private int newCount;

    /**
     * Constructor of immutable instance.
     * @param count setter of the count
     */
    SetCountAction(int count) {
        newCount = count;
    }

    /**
     * Getter method for count integer.
     * @return count
     */
    public int getCount() {
        return newCount;
    }
}