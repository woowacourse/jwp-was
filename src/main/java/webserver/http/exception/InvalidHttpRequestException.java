package webserver.http.exception;

public class InvalidHttpRequestException extends RuntimeException {
    public InvalidHttpRequestException(String message) {
        super(message);
    }
}
