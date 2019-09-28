package http;

public class HttpBody {
    private final String body;

    public HttpBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return body;
    }
}
