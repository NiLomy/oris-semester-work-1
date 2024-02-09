package ru.kpfu.itis.lobanov.util;

import ru.kpfu.itis.lobanov.util.configurations.ConfigProvider;
import ru.kpfu.itis.lobanov.util.configurations.DatabaseConfigProvider;
import ru.kpfu.itis.lobanov.util.constants.LogMessages;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.exception.ConfigException;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionProvider {
    public static final String DRIVER = "org.postgresql.Driver";

    private static Connection connection;

    public static Connection getConnection() throws DbException {
        if (connection == null) {
            try {
                ConfigProvider configProvider = new DatabaseConfigProvider();
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(
                        configProvider.readData(ServerResources.DB_URL_KEY),
                        configProvider.readData(ServerResources.DB_USER_KEY),
                        configProvider.readData(ServerResources.DB_PASSWORD_KEY)
                );
            } catch (SQLException | ClassNotFoundException e) {
                throw new DbException(LogMessages.ESTABLISH_CONNECTION_DB_EXCEPTION, e);
            } catch (ConfigException e) {
                throw new DbException(LogMessages.GET_CONFIGURATION_DB_EXCEPTION, e);
            }
        }
        return connection;
    }

    private DatabaseConnectionProvider() {
    }
}
