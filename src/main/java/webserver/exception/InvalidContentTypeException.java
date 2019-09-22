package webserver.exception;

public class InvalidContentTypeException extends RuntimeException {
    public InvalidContentTypeException(String message) {
        super(message);
    }
}
