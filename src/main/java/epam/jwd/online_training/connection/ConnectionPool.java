package epam.jwd.online_training.connection;

import epam.jwd.online_training.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger LOG = LogManager.getLogger(ConnectionPool.class);
    private static final String INITIALIZATION_CONNECTION_POOL_EXCEPTION = "Exception in initialization of connection pool!";
    private static final String GET_CONNECTION_POOL_EXCEPTION = "Exception occurred getting connection! ";
    private static final String TERMINATE_CONNECTION_POOL_EXCEPTION = "Exception occurred terminating connection pool! ";
    private static final String CREATE_CONNECTION_EXCEPTION = "Exception occurred creating connection! ";

    private static Lock lock = new ReentrantLock();
    private static ConnectionPool instance;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private BlockingQueue<ProxyConnection> connectionBlockingQueue;

    public static ConnectionPool getInstance() {
        if (!isInitialized.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInitialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() { initConnectionPool(); }

    private void initConnectionPool() {
        connectionBlockingQueue = new ArrayBlockingQueue<>(ConnectionConfig.poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            insertConnectionsIntoQueue(connectionBlockingQueue);
        } catch (SQLException e) {
            LOG.error(INITIALIZATION_CONNECTION_POOL_EXCEPTION, e);
            throw new RuntimeException(INITIALIZATION_CONNECTION_POOL_EXCEPTION, e);
        }
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionBlockingQueue.take();
        } catch (InterruptedException e) {
            LOG.error(GET_CONNECTION_POOL_EXCEPTION, e);
        }
        return connection;
    }

    public void terminatePool() {
        for (int i = 0; i < connectionBlockingQueue.size(); i++) {
            try {
                ProxyConnection connection = connectionBlockingQueue.take();
                connection.hardCloseConnection();
            } catch (InterruptedException | SQLException e) {
                LOG.error(TERMINATE_CONNECTION_POOL_EXCEPTION, e);
            }
        }
    }

    public void releaseConnection(ProxyConnection connection) {
        connectionBlockingQueue.offer(connection);
    }

    private void insertConnectionsIntoQueue(BlockingQueue<ProxyConnection> connectionBlockingQueue) {
        for (int i = 0; i < ConnectionConfig.poolSize; i++) {
            try {
                ProxyConnection connection = ConnectionCreator.createConnection();
                connectionBlockingQueue.offer(connection);
            } catch (PoolException e) {
                LOG.error(CREATE_CONNECTION_EXCEPTION, e);
                throw new RuntimeException(CREATE_CONNECTION_EXCEPTION, e);
            }
        }
    }
}