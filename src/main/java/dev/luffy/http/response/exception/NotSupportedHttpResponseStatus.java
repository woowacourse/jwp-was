package dev.luffy.http.response.exception;

public class NotSupportedHttpResponseStatus extends RuntimeException {
    public NotSupportedHttpResponseStatus(String message) {
        super(message);
    }
}
