package epam.jwd.online_training.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class ConnectionConfig {

    private static final Logger LOG = LogManager.getLogger(ConnectionConfig.class);
    private static final String COULD_NOT_LOAD_CONFIGURATION_DATA_FOR_CONNECTING =
            "Couldn't load configuration data for connecting to DataBase!!!";
    private static final String PATH_TO_PROPERTIES = "resources/database.properties";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/online_training_db?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "user";
    private static final String DB_MIN_POOL_SIZE = "2";
    private static final String DB_MAX_POOL_SIZE = "8";

    private String urlDb;
    private String driverDb;
    private String userDb;
    private String passDb;
    private int minPoolSize;
    private int maxPoolSize;

    ConnectionConfig() { initData(); }

    public String getUrlDb() { return urlDb; }

    public String getDriverDb() { return driverDb; }

    public String getUserDb() { return userDb; }

    public String getPassDb() { return passDb; }

    public int getMinPoolSize() { return minPoolSize; }

    public int getMaxPoolSize() { return maxPoolSize; }

    private void initData() {
        Properties property = new Properties();
        try {
            property.load(getClass().getClassLoader().getResourceAsStream(PATH_TO_PROPERTIES));
        } catch (IOException e) {
            LOG.error(COULD_NOT_LOAD_CONFIGURATION_DATA_FOR_CONNECTING, e);
        }
        urlDb = property.getProperty("db.url", DB_URL);
        driverDb = property.getProperty("db.driver", DB_DRIVER);
        userDb = property.getProperty("db.user", DB_USER);
        passDb = property.getProperty("db.password", DB_PASSWORD);
        minPoolSize = Integer.parseInt(property.getProperty("db.minPoolSize", DB_MIN_POOL_SIZE));
        maxPoolSize = Integer.parseInt(property.getProperty("db.maxPoolSize", DB_MAX_POOL_SIZE));
    }
}
