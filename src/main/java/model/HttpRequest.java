package model;

public class HttpRequest {
    private final HttpHeader header;

    public HttpRequest(HttpHeader header) {
        this.header = header;
    }

    public HttpService getService() {
        return header.getRequestLine()
                .getMethod()
                .getService();
    }

    public HttpHeader getHeader() {
        return header;
    }
}
