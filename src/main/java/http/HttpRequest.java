package http;

import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final Map<String, String> params;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, Map<String, String> params) {
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

    public Map<String, String> getParams() {
        return params;
    }

    public String getUrl() {
        return requestLine.getUrl();
    }
}
