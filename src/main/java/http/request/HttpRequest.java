package http.request;

import http.request.core.RequestHeader;
import http.request.core.RequestMethod;
import http.request.core.RequestPath;
import http.request.core.RequestVersion;

import java.util.List;
import java.util.Map;

public class HttpRequest {
    private List<Object> firstLineTokens;
    private Map<String, String> headers;
    private Map<String, String> data;

    HttpRequest(List<Object> firstLineTokens, RequestHeader headers, Map<String, String> data) {
        this.firstLineTokens = firstLineTokens;
        this.headers = headers.getHeaders();
        this.data = data;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getData() {
        return data;
    }

    public RequestMethod getRequestMethod() {
        return (RequestMethod) firstLineTokens.get(0);
    }

    public RequestPath getRequestPath() {
        return (RequestPath) firstLineTokens.get(1);
    }

    public RequestVersion getRequestVersion() {
        return (RequestVersion) firstLineTokens.get(2);
    }
}
