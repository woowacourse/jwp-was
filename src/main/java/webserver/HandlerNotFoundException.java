package webserver;

public class HandlerNotFoundException extends RuntimeException {
    public HandlerNotFoundException(final String message) {
        super(message);
    }
}
