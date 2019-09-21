package http;

public class HttpRequest {
    // TODO: HttpRequestTest 구현

    private RequestLine requestLine;
    private RequestHeader header;

    public HttpRequest(final RequestLine requestLine, final RequestHeader header) {
        this.requestLine = requestLine;
        this.header = header;
    }
}
