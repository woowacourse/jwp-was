package webserver.exception;

public class DuplicatedMappedRequestException extends RuntimeException {
    public DuplicatedMappedRequestException(final String message) {
        super(message);
    }
}
