/**
 * Data object to hold parameters for command info action.
 */
class CommandInfoAction implements IAction {

    /**
     * String indicating which action to show info about.
     */
    String thisCommand;

    /**
     * Constructor for immutable object.
     * @param command command to show info about
     */
    CommandInfoAction(String command) {
        thisCommand = command;
    }

    /**
     * Getter method.
     * @return command to show info about
     */
    public String getCommand() {
        return thisCommand;
    }
}