package http.exception;

public class NotMatchPasswordException extends RuntimeException{
    public NotMatchPasswordException(String message) {
        super(message);
    }
}
