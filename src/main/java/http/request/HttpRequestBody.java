package http.request;

public class HttpRequestBody {
    private byte[] body;

    public HttpRequestBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }
}
