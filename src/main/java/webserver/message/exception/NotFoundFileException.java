package webserver.message.exception;

public class NotFoundFileException extends RuntimeException {
    public NotFoundFileException() {
        super();
    }

    public NotFoundFileException(String message) {
        super(message);
    }
}
