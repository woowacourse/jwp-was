package dev.luffy.http.excption;

public class NotSupportedHttpRequestMethodException extends RuntimeException {
    public NotSupportedHttpRequestMethodException(String message) {
        super(message);
    }
}
