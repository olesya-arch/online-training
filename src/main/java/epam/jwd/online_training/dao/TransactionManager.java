package epam.jwd.online_training.dao;

import epam.jwd.online_training.connection.ConnectionPool;
import epam.jwd.online_training.connection.ProxyConnection;
import epam.jwd.online_training.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionManager implements AutoCloseable {

    private static final Logger LOG = LogManager.getLogger(TransactionManager.class);
    private static final String SET_AUTO_COMMIT_FALSE_PROBLEM = "Problem occurred trying to set auto commit false! ";
    private static final String SET_AUTO_COMMIT_TRUE_PROBLEM = "Problem occurred trying to set auto commit true! ";
    private static final String TRANSACTION_COMMIT_PROBLEM = "Transaction commit false! ";
    private static final String ROLLBACK_TRANSACTION_PROBLEM = "Problem occurred trying to rollback the transaction! ";
    private static TransactionManager instance;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static ThreadLocal<ProxyConnection> connectionThreadLocal = new ThreadLocal<>();

    public static TransactionManager launchTransaction(AbstractDao ...abstractDaos) throws SQLException {
        return launchTransaction(Isolation.REPEATABLE_READ, abstractDaos);
    }

    private static TransactionManager launchTransaction(Isolation isolationLevel, AbstractDao ...abstractDaos)
            throws SQLException {
        TransactionManager instance = instantiate();
        initialize(abstractDaos);

        ProxyConnection connection = connectionThreadLocal.get();
        connection.setTransactionIsolation(isolationLevel.level);
        instance.beginTransaction();
        return instance;
    }

    public static TransactionManager launchQuery(AbstractDao daos) throws SQLException {
        TransactionManager instance = instantiate();
        initialize(daos);
        ProxyConnection connection = connectionThreadLocal.get();
        connection.setReadOnly(true);
        return instance;
    }

    private static TransactionManager instantiate(AbstractDao ...daos) {
        if (!isInitialized.get()) {
            lock.lock();
            try{
                if (instance == null) {
                    instance = new TransactionManager();
                    isInitialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private static void initialize(AbstractDao ...daos) {
        ProxyConnection connection = null;
        try {
            connection = connectionPool.getConnection();
        } catch (PoolException e) {
            e.printStackTrace();
        }
        connectionThreadLocal.set(connection);
        for (AbstractDao dao : daos) {
            AbstractDao.insertConnection(connection);
        }
    }

    private void beginTransaction() {
        ProxyConnection connection = connectionThreadLocal.get();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOG.error(SET_AUTO_COMMIT_FALSE_PROBLEM, e);
        }
    }

    @Override
    public void close() throws SQLException {
        ProxyConnection connection = connectionThreadLocal.get();
        if (connection.isReadOnly()) {
            connection.setReadOnly(false);
            closeConnection();
            return;
        }
        try {
            commit();
            rollback();
        } finally {
            finishTransaction();
        }
    }

    private void closeConnection() {
        ProxyConnection connection = connectionThreadLocal.get();
            connection.close();
        }

    private void commit() throws SQLException {
        ProxyConnection connection = connectionThreadLocal.get();
        try {
            connection.commit();
        } catch (SQLException e) {
            LOG.error(TRANSACTION_COMMIT_PROBLEM, e);
            throw e;
        }
    }

    private void rollback() {
        ProxyConnection connection = connectionThreadLocal.get();
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOG.error(ROLLBACK_TRANSACTION_PROBLEM,e);
        }
    }

    private void finishTransaction() {
        ProxyConnection connection = connectionThreadLocal.get();
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOG.error(SET_AUTO_COMMIT_TRUE_PROBLEM, e);
        } finally {
            closeConnection();
        }
    }

    private enum Isolation {
        NONE(Connection.TRANSACTION_NONE),
        READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
        READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
        REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
        SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

        private final int level;

        Isolation(int level) { this.level = level; }
    }
}
