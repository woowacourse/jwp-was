package webserver.http.response;

public class HttpResponseBody {
    private byte[] body;

    private HttpResponseBody(final byte[] body) {
        this.body = body;
    }

    public static HttpResponseBody empty() {
        return new HttpResponseBody(new byte[]{});
    }

    public static HttpResponseBody of(byte[] body) {
        return new HttpResponseBody(body);
    }

    public byte[] getBody() {
        return body;
    }

    public int getLength() {
        return body.length;
    }
}
