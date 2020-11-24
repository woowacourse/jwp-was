package http.was.exception;

public class DuplicatedMappedRequestException extends RuntimeException {
    public DuplicatedMappedRequestException(String message) {
        super(message);
    }
}
