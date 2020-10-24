package webserver.http.request;

import webserver.http.service.AbstractHttpService;

public class HttpRequest {
    private final HttpHeader header;
    private final HttpBody body;

    public HttpRequest(HttpHeader header, HttpBody body) {
        this.header = header;
        this.body = body;
    }

    public AbstractHttpService createService() {
        return header.getMethod()
                .createService(header, body);
    }

    public HttpHeader getHeader() {
        return header;
    }

    public HttpBody getBody() {
        return body;
    }
}
