package model;

public class HttpRequest {
    private final HttpHeader header;
    private final HttpBody body;

    public HttpRequest(HttpHeader header, HttpBody body) {
        this.header = header;
        this.body = body;
    }

    public AbstractHttpService getService() {
        return header.getMethod()
                .getService(header, body);
    }

    public HttpHeader getHeader() {
        return header;
    }

    public HttpBody getBody() {
        return body;
    }
}
