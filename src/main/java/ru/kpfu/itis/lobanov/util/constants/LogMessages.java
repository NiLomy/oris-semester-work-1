package ru.kpfu.itis.lobanov.util.constants;

public class LogMessages {
    public static final String ILLEGAL_ARGUMENTS_COUNT_DB_CONFIG_EXCEPTION = "There is arguments count mismatch. Expected: %d, found: %d.";
    public static final String WRITE_DB_CONFIG_EXCEPTION = "Can not write data into the property file.";
    public static final String READ_DB_CONFIG_EXCEPTION = "Can not read data from the property file.";
    public static final String NOT_FOUND_DB_CONFIG_EXCEPTION = "Unable to find: %s.";
    public static final String GET_CONFIGURATION_CLOUDINARY_EXCEPTION = "Can not get configurations for current cloud.";
    private LogMessages() {
    }
}
