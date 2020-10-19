package model;

public class HttpRequest {
    private final HttpHeader header;
    private final HttpBody body;

    public HttpRequest(HttpHeader header, HttpBody body) {
        this.header = header;
        this.body = body;
    }

    public HttpService getService() {
        return header.getRequestLine()
                .getMethod()
                .getService();
    }

    public HttpHeader getHeader() {
        return header;
    }

    public HttpBody getBody() {
        return body;
    }
}
