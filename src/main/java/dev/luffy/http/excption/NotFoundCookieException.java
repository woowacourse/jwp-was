package dev.luffy.http.excption;

public class NotFoundCookieException extends RuntimeException {
    public NotFoundCookieException(String message) {
        super(message);
    }
}
