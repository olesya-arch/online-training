package epam.jwd.online_training.exception;

public class CommandException extends Exception {

    private static final long serialVersionUID = -7644765889782150651L;

    public CommandException() {
    }

    public CommandException(String message) { super(message);}

    public CommandException(String message, Throwable cause) { super(message, cause);}

    public CommandException(String s, String str, Throwable cause) { super(cause);}
}
