package http.exceptions;

public class NotSupportedRequestException extends RuntimeException {
    public NotSupportedRequestException() {
    }

    public NotSupportedRequestException(String message) {
        super(message);
    }
}
