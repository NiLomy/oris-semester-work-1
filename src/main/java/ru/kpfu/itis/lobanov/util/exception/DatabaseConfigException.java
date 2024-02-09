package ru.kpfu.itis.lobanov.util.exception;

public class DatabaseConfigException extends ConfigException {
    public DatabaseConfigException() {
    }

    public DatabaseConfigException(String message) {
        super(message);
    }

    public DatabaseConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseConfigException(Throwable cause) {
        super(cause);
    }

    public DatabaseConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
