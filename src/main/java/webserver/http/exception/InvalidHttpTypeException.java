package webserver.http.exception;

public class InvalidHttpTypeException extends RuntimeException {
    public InvalidHttpTypeException(String errorMessage) {
        super(errorMessage);
    }
}
