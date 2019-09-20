package http;

public class NotFoundHeaderException extends RuntimeException {
    public NotFoundHeaderException(String message) {
        super(message);
    }
}
