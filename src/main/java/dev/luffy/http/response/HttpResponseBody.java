package dev.luffy.http.response;

public class HttpResponseBody {

    private static final int EMPTY_BODY = 0;

    private final byte[] body;

    public HttpResponseBody() {
        this.body = new byte[EMPTY_BODY];
    }

    public HttpResponseBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodyLength() {
        return body.length;
    }
}
