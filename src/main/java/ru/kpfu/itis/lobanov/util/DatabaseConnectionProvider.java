package ru.kpfu.itis.lobanov.util;

import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionProvider {
    public static final String DRIVER = "org.postgresql.Driver";
    public static final String URL = "jdbc:postgresql://localhost:5432/religious studying";
    public static final String USER = "postgres";
    public static final String PASSWORD = "nikita2004";

    private static Connection connection;

    public static Connection getConnection() throws DbException {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );
            } catch (SQLException | ClassNotFoundException e) {
                throw new DbException("Cannot connect to DB.", e);
            }
        }
        return connection;
    }

    private DatabaseConnectionProvider() {}
}
