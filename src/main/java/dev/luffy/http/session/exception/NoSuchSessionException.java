package dev.luffy.http.session.exception;

public class NoSuchSessionException extends RuntimeException {
    public NoSuchSessionException(String message) {
        super(message);
    }
}
