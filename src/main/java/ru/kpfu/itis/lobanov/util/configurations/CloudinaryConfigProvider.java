package ru.kpfu.itis.lobanov.util.configurations;

import ru.kpfu.itis.lobanov.util.constants.LogMessages;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.exception.CloudinaryConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class CloudinaryConfigProvider extends ConfigProvider {
    public static final String PROPERTY_FILE_NAME = "properties/cloudinary-config.properties";
    public static final int PROVIDED_ARGUMENTS_COUNT = 3;

    public void writeData(String... data) throws CloudinaryConfigException {
        if (data.length != PROVIDED_ARGUMENTS_COUNT) throw new CloudinaryConfigException(String.format(LogMessages.ILLEGAL_ARGUMENTS_COUNT_DB_CONFIG_EXCEPTION, PROVIDED_ARGUMENTS_COUNT, data.length));

        try (OutputStream output = Files.newOutputStream(Paths.get(PROPERTY_FILE_PATH + PROPERTY_FILE_NAME))) {
            Properties prop = new Properties();

            prop.setProperty(ServerResources.CLOUDINARY_NAME_KEY, data[0]);
            prop.setProperty(ServerResources.CLOUDINARY_API_KEY, data[1]);
            prop.setProperty(ServerResources.CLOUDINARY_API_SECRET_KEY, data[2]);

            prop.store(output, null);
        } catch (IOException e) {
            throw new CloudinaryConfigException(LogMessages.WRITE_DB_CONFIG_EXCEPTION, e);
        }
    }

    public String readData(String key) throws CloudinaryConfigException {
        try (InputStream input = CloudinaryConfigProvider.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {
            Properties prop = new Properties();

            if (input == null) {
                throw new CloudinaryConfigException(String.format(LogMessages.NOT_FOUND_DB_CONFIG_EXCEPTION, PROPERTY_FILE_NAME));
            }

            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException e) {
            throw new CloudinaryConfigException(LogMessages.READ_DB_CONFIG_EXCEPTION, e);
        }
    }
}
