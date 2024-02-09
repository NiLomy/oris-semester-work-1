package ru.kpfu.itis.lobanov.util.exception;

public class CloudinaryException extends RuntimeException {
    public CloudinaryException() {
    }

    public CloudinaryException(String message) {
        super(message);
    }

    public CloudinaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudinaryException(Throwable cause) {
        super(cause);
    }

    public CloudinaryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
