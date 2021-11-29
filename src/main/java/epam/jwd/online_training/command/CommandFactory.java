package epam.jwd.online_training.command;

import epam.jwd.online_training.content.RequestContent;

public class CommandFactory {

    private static final String COMMAND_PARAMETER = "command";

    public Command initCommand(RequestContent content) {
        Command command = null;
        String commandName = null;
        String enteringCommandName = content.getSingleRequestParameter(COMMAND_PARAMETER);
        if (enteringCommandName != null && !enteringCommandName.isEmpty()) {
            commandName = enteringCommandName.toUpperCase();
            command = CommandMap.defineCommandType(commandName);
        }
        return command;
    }
}
