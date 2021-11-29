package epam.jwd.online_training.exception;

public class PoolException extends Exception {

    private static final long serialVersionUID = 5603895382595004611L;

    public PoolException() {

    }

    public PoolException(String message) { super(message); }

    public PoolException(Throwable cause) { super(cause); }

    public PoolException(String message, Throwable cause) { super(message, cause); }
}
