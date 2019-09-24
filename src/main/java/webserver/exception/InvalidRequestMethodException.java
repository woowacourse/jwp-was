package webserver.exception;

public class InvalidRequestMethodException extends RuntimeException {
    public InvalidRequestMethodException() {
        super("Invalid Request Method");
    }
}
