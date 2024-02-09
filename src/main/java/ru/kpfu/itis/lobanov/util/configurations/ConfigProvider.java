package ru.kpfu.itis.lobanov.util.configurations;

import ru.kpfu.itis.lobanov.util.exception.ConfigException;

public abstract class ConfigProvider {
    public static final String PROPERTY_FILE_PATH = "src/main/resources/";

    abstract void writeData(String... data) throws ConfigException;

    public abstract String readData(String key) throws ConfigException;
}
