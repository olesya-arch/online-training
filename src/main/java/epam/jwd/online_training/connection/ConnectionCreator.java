package epam.jwd.online_training.connection;

import epam.jwd.online_training.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCreator {

    private static final Logger LOG = LogManager.getLogger(ConnectionCreator.class);
    private static final String FAILED_CREATING_CONNECTION_EXCEPTION = "Failed creating connection! ";

    static ProxyConnection createConnection() throws PoolException {
        try {
            String url = ConnectionConfig.urlDb;
            String user = ConnectionConfig.userDb;
            String password = ConnectionConfig.passDb;
            Connection connection = DriverManager.getConnection(url, user, password);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            LOG.error(FAILED_CREATING_CONNECTION_EXCEPTION, e);
            throw new PoolException(FAILED_CREATING_CONNECTION_EXCEPTION, e);
        }
    }
}
