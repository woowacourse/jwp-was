package dev.luffy.http.excption;

public class NotFoundExtensionException extends RuntimeException {
    public NotFoundExtensionException(String message) {
        super(message);
    }
}
