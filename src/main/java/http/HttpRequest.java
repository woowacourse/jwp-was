package http;

public class HttpRequest {

    private RequestLine requestLine;
    private RequestHeader header;

    public HttpRequest(final RequestLine requestLine, final RequestHeader header) {
        this.requestLine = requestLine;
        this.header = header;
    }
}
