package ru.kpfu.itis.lobanov.util.exception;

public class CloudinaryConfigException extends ConfigException {
    public CloudinaryConfigException() {
    }

    public CloudinaryConfigException(String message) {
        super(message);
    }

    public CloudinaryConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudinaryConfigException(Throwable cause) {
        super(cause);
    }

    public CloudinaryConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
