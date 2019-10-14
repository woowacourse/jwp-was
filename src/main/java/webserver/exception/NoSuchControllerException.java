package webserver.exception;

public class NoSuchControllerException extends RuntimeException {
    public NoSuchControllerException() {
        super();
    }

    public NoSuchControllerException(String message) {
        super(message);
    }
}
