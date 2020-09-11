package http;

import java.util.Map;

public class HttpRequest {
    private final RequestUri requestUri;
    private final RequestHeader requestHeader;
    private final Map<String, String> params;

    public HttpRequest(RequestUri requestUri, RequestHeader requestHeader, Map<String, String> params) {
        this.requestUri = requestUri;
        this.requestHeader = requestHeader;
        this.params = params;
    }

    public RequestUri getRequestUri() {
        return requestUri;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
