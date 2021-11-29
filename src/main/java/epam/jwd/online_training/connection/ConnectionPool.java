package epam.jwd.online_training.connection;

import epam.jwd.online_training.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool implements AutoCloseable {

    private static final Logger LOG = LogManager.getLogger(ConnectionPool.class);
    private static final String INVALID_CLASS_NAME = "Driver ClassName is invalid! Cannot be created connection pool! ";
    private static final String NOT_ENOUGH_CONNECTIONS_TO_START =
            "Not enough connections for start! Cannot be created connection pool! ";
    private static final String CONNECTION_POOL_WAS_CREATED = "Connection pool was created with {} of active " +
            " connections and max capacity = {}";
    private static final String EXCEPTION_GETTING_CONNECTION_POOL = "Exception occurred getting connection pool! ";
    private static final String CANNOT_CONNECT_TO_DATABASE = "Cannot connect to database! ";
    private static final String CONNECTION_IS_STOPPED = "Attempt to get connection when ConnectionPool is stopped ";
    private static final String CONNECTION_WAS_CREATED = "Additional connection was created, size of connection pool now is: ";
    private static final String CANNOT_GET_NEW_CONNECTION = "Cannot get new connection! ";
    private static final String NO_FREE_CONNECTION = "No free connections, wait until connection will be available. ";
    private static final String CONNECTION_WAS_RETURNED_TO_POOL = "Connection was returned to connection pool ";
    private static final String CANNOT_RETURN_CONNECTION = "Cannot return connection. Exception occurred trying to close connection. ";
    private static final String POOL_TERMINATION_EXCEPTION = "Exception occurred during pool termination ";
    private static final String CANNOT_DEREGISTER_DRIVER = "Cannot deregister driver closing connection ";
    private static final String CONNECTION_POOL_WAS_SHUTDOWN = "ConnectionPool was shutdown ";

    private static Lock lock = new ReentrantLock(true);
    private static ConnectionPool pool;
    private ConnectionConfig connectionConfig;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static AtomicBoolean isRunning = new AtomicBoolean(false);
    private static AtomicInteger lastConnectionNumber = new AtomicInteger(0);
    private BlockingQueue<ProxyConnection> connectionBlockingQueue;

    private ConnectionPool() throws PoolException {
        try {
            connectionConfig = new ConnectionConfig();
            Class.forName(connectionConfig.getDriverDb());
            connectionBlockingQueue = new ArrayBlockingQueue<>(connectionConfig.getMaxPoolSize(), true);
            for (int i = 0; i < connectionConfig.getMinPoolSize(); i++)
                connectionBlockingQueue.add(createConnection());
        } catch (ClassNotFoundException e) {
            LOG.error(INVALID_CLASS_NAME, e);
            throw new PoolException(e);
        } catch (PoolException e) {
            LOG.error(NOT_ENOUGH_CONNECTIONS_TO_START, e);
            throw new PoolException(e);
        }
        isRunning.set(true);
        LOG.info(CONNECTION_POOL_WAS_CREATED, connectionBlockingQueue.size(),connectionConfig.getMaxPoolSize());
    }

    public static ConnectionPool getInstance(){
        if (!isCreated.get()) {
            try {
                lock.lock();
                if (pool == null) {
                    pool = new ConnectionPool();
                    isCreated.set(true);
                }
            } catch (PoolException e) {
                LOG.error(EXCEPTION_GETTING_CONNECTION_POOL);
            } finally {
                lock.unlock();
            }
        }
        return pool;
    }

    private ProxyConnection createConnection() throws PoolException {
        try {
            return new ProxyConnection(DriverManager.getConnection(connectionConfig.getUrlDb(),
                    connectionConfig.getUserDb(),
                    connectionConfig.getPassDb()));
        } catch (SQLException e) {
            throw new PoolException(CANNOT_CONNECT_TO_DATABASE, e);
        }
    }

    public ProxyConnection getConnection() throws PoolException {
        if (!isRunning.get()) {
            throw new PoolException(CONNECTION_IS_STOPPED);
        }
        ProxyConnection connection = null;
        try {
            lock.lock();
            if (connectionBlockingQueue.isEmpty() && lastConnectionNumber.get() < connectionConfig.getMaxPoolSize()) {
                try {
                    connection = createConnection();
                    LOG.info(CONNECTION_WAS_CREATED + connectionBlockingQueue.size());
                } catch (PoolException e) {
                    LOG.error(CANNOT_GET_NEW_CONNECTION, e);
                }
            } else {
                connection = connectionBlockingQueue.poll();
            }
            if (connection != null) {
                lastConnectionNumber.incrementAndGet();
            } else {
                throw new PoolException(NO_FREE_CONNECTION);
            }
        } finally {
            lock.unlock();
        }
        return connection;
    }

    public void returnConnection(ProxyConnection connection) {
        boolean isReturned = connectionBlockingQueue.offer(connection);
        if (isReturned) {
            LOG.info(CONNECTION_WAS_RETURNED_TO_POOL);
        } else {
            try {
                connection.hardCloseConnection();
            } catch (SQLException e) {
                LOG.error(CANNOT_RETURN_CONNECTION, e);
            }
        }
        lastConnectionNumber.decrementAndGet();
    }

    public void terminatePool(){
        for (int i = 0; i < connectionBlockingQueue.size(); i++) {
            try {
                ProxyConnection connection = connectionBlockingQueue.take();
                connection.hardCloseConnection();
            } catch (SQLException | InterruptedException e) {
                LOG.error(POOL_TERMINATION_EXCEPTION, e);
            }
        }
    }

    @Override
    public void close() {
        isRunning.set(false);
        for (ProxyConnection connection : connectionBlockingQueue) {
            connection.close();
        }
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_DEREGISTER_DRIVER, e);
        }
        LOG.info(CONNECTION_POOL_WAS_SHUTDOWN);
    }
}