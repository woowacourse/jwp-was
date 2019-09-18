package http.supoort;

public class IllegalHttpRequest extends RuntimeException {

    public IllegalHttpRequest() {
    }

    public IllegalHttpRequest(String message) {
        super(message);
    }
}
