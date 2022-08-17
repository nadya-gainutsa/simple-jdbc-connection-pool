package org.example;

import org.example.connectionpool.PooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {
    private static final String URL = "jdbc:postgresql://localhost:5432/database_name";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) throws SQLException {
        var dataSource = new PooledDataSource(URL, USER, PASSWORD);

        try(Connection connection = dataSource.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                var resSet = statement.executeQuery("select * from products");
                while (resSet.next()) {
                    System.out.println(resSet.next());
                }
            }
        }


    }
}
