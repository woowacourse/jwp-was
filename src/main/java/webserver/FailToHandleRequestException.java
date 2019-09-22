package webserver;

public class FailToHandleRequestException extends RuntimeException {
    public FailToHandleRequestException() {
    }

    public FailToHandleRequestException(String message) {
        super(message);
    }
}
