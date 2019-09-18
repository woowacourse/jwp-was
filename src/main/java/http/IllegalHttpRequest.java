package http;

public class IllegalHttpRequest extends RuntimeException {

    public IllegalHttpRequest() {
    }

    public IllegalHttpRequest(String message) {
        super(message);
    }
}
