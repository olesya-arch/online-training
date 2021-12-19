package epam.jwd.online_training.dao;

import epam.jwd.online_training.connection.ProxyConnection;

public abstract class AbstractDao {

    protected static ThreadLocal<ProxyConnection> connectionThreadLocal = new ThreadLocal<ProxyConnection>(){};

    public static void insertConnection(ProxyConnection proxyConnection) {connectionThreadLocal.set(proxyConnection);}
}
