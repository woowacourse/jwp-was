package webserver.http;

public class HttpBody {
    private String body;

    public HttpBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public boolean isEmpty() {
        return body.isEmpty();
    }
}
