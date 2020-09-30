package webserver.http.response;

public class HttpResponseBody {
    private byte[] body;

    public HttpResponseBody(byte[] body) {
        this.body = body;
    }

    public boolean isNotEmpty() {
        return body.length != 0;
    }

    public byte[] getBody() {
        return body;
    }
}
