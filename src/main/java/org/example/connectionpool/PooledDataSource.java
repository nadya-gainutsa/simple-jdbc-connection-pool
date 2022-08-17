package org.example.connectionpool;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class PooledDataSource extends PGSimpleDataSource {
    private final transient Queue<Connection> connectionPool;
    private int size;

    public PooledDataSource(String url, String user, String password) throws SQLException {
        super.setURL(url);
        super.setUser(user);
        super.setPassword(password);
        this.connectionPool = new LinkedList<>();
        initializePool();
    }

    public void setSize(int size) {
        this.size = size;
    }

    private void initializePool() throws SQLException {
        if (size == 0) {
            size = 10;
        }
        for(int i = 0; i < size; i++) {
            Connection connection = super.getConnection();
            ConnectionProxy connectionProxy = new ConnectionProxy(connectionPool, connection);

            connectionPool.add(connectionProxy);
        }
    }

    @Override
    public Connection getConnection() {
        return connectionPool.poll();
    }
}
