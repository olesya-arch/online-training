package epam.jwd.online_training.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConnectionConfig {

    private static final Logger LOG = LogManager.getLogger(ConnectionConfig.class);
    private static final String COULD_NOT_READ_DATA_FROM_RESOURCE_BUNDLE_FILE =
            "Couldn't read data from resourceBundle file!!!";

    private static final String RESOURCE_BUNDLE_PATH = "database.properties";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_POOL_SIZE = "db.poolSize";

    private static ResourceBundle resourceBundle;
    static String urlDb;
    static String userDb;
    static String passDb;
    static int poolSize;

    static {
        try {
            resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);
            userDb = resourceBundle.getString(DB_USER);
            passDb = resourceBundle.getString(DB_PASSWORD);
            urlDb = resourceBundle.getString(DB_URL);
            String poolSizeValue = resourceBundle.getString(DB_POOL_SIZE);
            poolSize = Integer.parseInt(poolSizeValue);
        } catch (MissingResourceException e) {
            LOG.error(COULD_NOT_READ_DATA_FROM_RESOURCE_BUNDLE_FILE, e);
            throw new RuntimeException(COULD_NOT_READ_DATA_FROM_RESOURCE_BUNDLE_FILE, e);
        }
    }
}
