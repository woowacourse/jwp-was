package http.excption;

public class NoQueryParamsException extends RuntimeException {
    public NoQueryParamsException(String message) {
        super(message);
    }
}
