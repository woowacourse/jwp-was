package http.request;

import http.request.core.RequestHeader;
import http.request.core.RequestPath;

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

    public RequestPath getFirstLineTokens() {
        return (RequestPath) firstLineTokens.get(1);
    }
}
