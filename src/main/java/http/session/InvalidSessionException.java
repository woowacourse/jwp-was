package http.session;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException() {
    }

    public InvalidSessionException(String message) {
        super(message);
    }
}
