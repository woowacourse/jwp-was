package http.supoort;

public class FailToConvertMessageException extends RuntimeException {
    public FailToConvertMessageException() {
    }

    public FailToConvertMessageException(String message) {
        super(message);
    }
}
