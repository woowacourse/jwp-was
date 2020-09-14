package webserver.http.request;

public class RequestBody {
    private String body;

    public RequestBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public boolean isEmpty() {
        return body.isEmpty();
    }
}
