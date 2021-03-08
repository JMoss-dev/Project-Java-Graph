package src.console;

import java.util.Scanner;

public abstract class ConsoleHelper {

    public ConsoleHelper() {
        this.commands = initCommands();
    }

    private final Command[] commands;

    private Scanner s;
    private boolean isListening = false;

    protected abstract Command[] initCommands();

    /*
     * provides a scanner that reads incoming text from the console
     */

    public final void startListening() {
        System.out.println("Dynamic Graph System:");
        isListening = true;
        s = new Scanner(System.in);
        receiveInput();
    }

    public final void stopListening() {
        isListening = false;
        s.close();
    }

    private void receiveInput() {
        if (isListening) {
            String input = s.nextLine();
            String[] parameters = input.split(" ");
            handleInput(parameters);

            receiveInput();
        }
    }

    private void handleInput(String[] input) {

        String commandName = input[0];
        Command command = getCommand(commandName);
        if (command == null) {
            onCommandNotFound(commandName);
            return;
        }
        String[] parameters = new String[input.length - 1];
        for (int i = 1; i < input.length; i++) {
            parameters[i - 1] = input[i];
        }
        if (!onHandleInput(command, parameters))
            return;
        command.onCommand(parameters);

    }

    protected abstract boolean onHandleInput(Command command, String[] parameters);

    /*
     * prints the parameters of the given command
     */

    protected final void printParameters(Command command) {

        StringBuilder messageBuilder = new StringBuilder(command.getName());
        for (String parameter : command.getParameters()) {
            messageBuilder.append(" ").append(parameter);
        }
        System.out.println(messageBuilder.toString());

    }


    protected void onCommandNotFound(String name) {
        System.out.println("The command " + name + " does not exist. Please type help for more information.");
    }

    protected void onInvalidArguments(Command command) {
        System.out.println("Invalid parameters. Please try it like this: ");
        printParameters(command);
    }

    /*
     * returns an instance of Command matching with the given String
     */

    protected final Command getCommand(String command) {

        for (Command command1 : commands) {
            if (command1.getName().equals(command)) {
                return command1;
            }
        }
        return null;
    }

    protected final Command[] getCommands() {
        return commands;
    }
}
