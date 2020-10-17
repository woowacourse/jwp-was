package model;

public class HttpHeader {
    private final RequestLine requestLine;

    public HttpHeader(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }
}
