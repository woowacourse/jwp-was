package http;

public class StaticResourceTypeNotFoundException extends RuntimeException {
    public StaticResourceTypeNotFoundException(final String message) {
        super(message);
    }
}
