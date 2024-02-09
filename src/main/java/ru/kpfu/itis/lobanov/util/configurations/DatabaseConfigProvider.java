package ru.kpfu.itis.lobanov.util.configurations;

import ru.kpfu.itis.lobanov.util.constants.LogMessages;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.exception.DatabaseConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DatabaseConfigProvider extends ConfigProvider {
    public static final String PROPERTY_FILE_NAME = "properties/db-config.properties";
    public static final int PROVIDED_ARGUMENTS_COUNT = 3;

    public void writeData(String... data) throws DatabaseConfigException {
        if (data.length != PROVIDED_ARGUMENTS_COUNT) throw new DatabaseConfigException(String.format(LogMessages.ILLEGAL_ARGUMENTS_COUNT_DB_CONFIG_EXCEPTION, PROVIDED_ARGUMENTS_COUNT, data.length));

        try (OutputStream output = Files.newOutputStream(Paths.get(PROPERTY_FILE_PATH + PROPERTY_FILE_NAME))) {
            Properties prop = new Properties();

            prop.setProperty(ServerResources.DB_URL_KEY, data[0]);
            prop.setProperty(ServerResources.DB_USER_KEY, data[1]);
            prop.setProperty(ServerResources.DB_PASSWORD_KEY, data[2]);

            prop.store(output, null);
        } catch (IOException e) {
            throw new DatabaseConfigException(LogMessages.WRITE_DB_CONFIG_EXCEPTION, e);
        }
    }

    public String readData(String key) throws DatabaseConfigException {
        try (InputStream input = DatabaseConfigProvider.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {
            Properties prop = new Properties();

            if (input == null) {
                throw new DatabaseConfigException(String.format(LogMessages.NOT_FOUND_DB_CONFIG_EXCEPTION, PROPERTY_FILE_NAME));
            }

            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException e) {
            throw new DatabaseConfigException(LogMessages.READ_DB_CONFIG_EXCEPTION, e);
        }
    }
}
