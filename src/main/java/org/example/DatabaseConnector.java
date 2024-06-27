package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;

@Getter
public class DatabaseConnector implements AutoCloseable {

    private static final String URL = "jdbc:postgresql://localhost:5432/JDBCTest";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private Connection connection;

    public Connection connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connected to the database");
        return connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from the database");
        }
    }
}
