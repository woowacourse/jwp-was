package http.supoort;

public class NotSupportedRequestException extends RuntimeException {
    public NotSupportedRequestException() {
    }

    public NotSupportedRequestException(String message) {
        super(message);
    }
}
