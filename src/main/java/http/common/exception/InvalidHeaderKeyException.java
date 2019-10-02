package http.common.exception;

public class InvalidHeaderKeyException extends RuntimeException {
    public InvalidHeaderKeyException(String message) {
        super(message);
    }
}
