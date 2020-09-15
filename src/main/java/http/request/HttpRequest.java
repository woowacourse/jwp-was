package http.request;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestParams params;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestParams params) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.params = params;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestParams getParams() {
        return params;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }
}
