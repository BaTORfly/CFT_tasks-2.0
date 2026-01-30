package ru.batorfly.config.exceptions;

public class ConfigLoadingException extends RuntimeException {
    public ConfigLoadingException(String message) {
        super(message);
    }
    public ConfigLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
