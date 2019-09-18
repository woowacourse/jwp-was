package http.supoort;

public class NotSupportedHTTPMethod extends RuntimeException {
    public NotSupportedHTTPMethod() {
    }

    public NotSupportedHTTPMethod(String message) {
        super(message);
    }
}
