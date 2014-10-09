public class CommandInfoAction implements IAction {
    String command;

    CommandInfoAction(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}