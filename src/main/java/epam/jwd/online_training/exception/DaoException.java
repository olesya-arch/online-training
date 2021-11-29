package epam.jwd.online_training.exception;

import java.sql.SQLException;

public class DaoException extends Exception {

    private static final long serialVersionUID = -2742779989791089817L;

    public DaoException(String s, String str, SQLException e) {
    }
    public DaoException(String message) { super(message);}

    public DaoException(String message, Throwable cause) { super(message, cause);}

    public DaoException(Throwable cause) { super(cause);}
}
