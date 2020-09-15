package http.request;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestParams requestParams;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestParams requestParams) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestParams = requestParams;
    }

    public String getHeader(String key) {
        return requestHeader.get(key);
    }

    public String getParameter(String key) {
        return requestParams.get(key);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }
}
