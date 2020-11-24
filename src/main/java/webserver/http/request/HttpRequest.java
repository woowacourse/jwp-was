package webserver.http.request;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final HttpHeader header;
    private final String body;

    public HttpRequest(HttpRequestLine requestLine, HttpHeader header, String body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public RequestMapping toRequestMapping() {
        return new RequestMapping(requestLine.getRequestURI().getUri(), requestLine.getMethod());
    }

    public HttpRequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeader getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
