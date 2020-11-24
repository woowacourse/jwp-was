package webserver.http.response;

import webserver.http.BodyState;

public class HttpResponseBody {
    private final BodyState bodyState;
    private final byte[] body;

    private HttpResponseBody(BodyState bodyState, byte[] body) {
        this.bodyState = bodyState;
        this.body = body;
    }

    public static HttpResponseBody emptyResponseBody() {
        return new HttpResponseBody(BodyState.EMPTY, null);
    }

    public static HttpResponseBody add(byte[] body) {
        return new HttpResponseBody(BodyState.NOT_EMPTY, body);
    }

    public boolean isEmpty() {
        return bodyState.isEmpty();
    }

    public int getLength() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }
}
