package eu.donals;

import java.util.ArrayList;
import java.util.List;

public class InputParser {
    private Command command;
    private String argument;

    public enum Command {
        BACK,
        GO,
        MOVE,
        LOOK,
        TALK,
        SAY,
        ANSWER,
        HELP,
        INV,
        PAUSE,
        QUIT
    }

    public Command getCommand() { return command; }
    public String getArgument() { return argument; }

    private static Command parseCommand(String userCommand) {
        userCommand = userCommand.toUpperCase();
        if(!isCommand(userCommand)) {
            return Command.QUIT;
        } else {
            return Command.valueOf(userCommand);
        }
    }

    public static boolean isCommand(String userCommand) {
        Command[] commands = Command.values();
        //putting the command enums into an array
        List<String> stringCommands = new ArrayList<String>();
        for (Command cmd: commands) {
            stringCommands.add(cmd.toString());
        }
        return stringCommands.contains(userCommand);
    }

    private static String parseArgument(String userArgument) {
        // TODO: parse for faulty argument
        return userArgument;
    }

    public InputParser(String userInput) {
        String[] inputTokenized = userInput.split(" ", 2); // limits array to 2 (ex. ["look", "Stinkey Monkey"]

        this.command = parseCommand(inputTokenized[0]);

        if (inputTokenized.length == 1) {
            this.argument = parseArgument("");
        } else {
            this.argument = parseArgument(inputTokenized[1]);
        }

    }
}
