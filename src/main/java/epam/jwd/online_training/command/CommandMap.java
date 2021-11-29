package epam.jwd.online_training.command;

import java.util.EnumMap;
import java.util.Map;

public class CommandMap {

    private static final Map<CommandType, Command> commandMap = new EnumMap<>(CommandType.class);

    public CommandMap() {
    }

    public static Command defineCommandType(String command) {
        CommandType commandType = CommandType.valueOf(command);
        return commandMap.get(commandType);
    }
}
