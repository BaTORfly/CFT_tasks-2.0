package ru.batorfly.consumer;

public class ResourceConsumingException extends RuntimeException {
    public ResourceConsumingException(String message) {
        super(message);
    }
    public ResourceConsumingException(String message, Throwable cause) {
        super(message, cause);
    }
    public ResourceConsumingException(Throwable cause) {
        super(cause);
    }

    public ResourceConsumingException() {}
}
