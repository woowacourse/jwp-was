package http.model;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpParameters parameters;
    private final HttpHeaders httpHeaders;

    public HttpRequest(RequestLine requestLine, HttpParameters parameters, HttpHeaders httpHeaders) {
        this.requestLine = requestLine;
        this.parameters = parameters;
        this.httpHeaders = httpHeaders;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpParameters getParameters() {
        return parameters;
    }

    public HttpHeaders getHeaders() {
        return httpHeaders;
    }
}
