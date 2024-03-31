package ru.kpfu.itis.lobanov.util.exception;

public class MessageLikeNotFoundException extends RuntimeException {
    public MessageLikeNotFoundException() {
        super();
    }

    public MessageLikeNotFoundException(String message) {
        super(message);
    }

    public MessageLikeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageLikeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected MessageLikeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
