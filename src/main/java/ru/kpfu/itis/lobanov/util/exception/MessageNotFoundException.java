package ru.kpfu.itis.lobanov.util.exception;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException() {
        super();
    }

    public MessageNotFoundException(String message) {
        super(message);
    }

    public MessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageNotFoundException(Throwable cause) {
        super(cause);
    }

    protected MessageNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
