package webserver.exception;

public class InvalidContentTypeException extends RuntimeException {
    public InvalidContentTypeException() {
        super("invalid content type");
    }
}
