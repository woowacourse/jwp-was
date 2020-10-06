package webserver;

public abstract class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }
}
