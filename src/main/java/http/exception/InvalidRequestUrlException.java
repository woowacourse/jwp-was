package http.exception;

public class InvalidRequestUrlException extends RuntimeException {
    public InvalidRequestUrlException() {
        super("Invalid Request Url");
    }
}
