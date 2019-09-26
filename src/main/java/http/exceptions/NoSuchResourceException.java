package http.exceptions;

public class NoSuchResourceException extends RuntimeException {
    public NoSuchResourceException() {
    }

    public NoSuchResourceException(String message) {
        super(message);
    }
}
