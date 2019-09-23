package webserver.message.exception;

public class UrlDecodeException extends RuntimeException {

    public UrlDecodeException() {
        super();
    }

    public UrlDecodeException(String message) {
        super(message);
    }
}
