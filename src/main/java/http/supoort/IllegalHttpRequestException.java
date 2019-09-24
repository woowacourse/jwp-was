package http.supoort;

public class IllegalHttpRequestException extends RuntimeException {

    public IllegalHttpRequestException() {
    }

    public IllegalHttpRequestException(String message) {
        super(message);
    }
}
