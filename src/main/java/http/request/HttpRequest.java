package http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final HttpRequestHeader httpRequestHeader;
    private final Map<String, String> cookies = new HashMap<>();
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequest(HttpRequestHeader header) {
        this(header, new HttpRequestBody(""));
    }

    public String getMethod() {
        return httpRequestHeader.getMethod();
    }

    public String getHeader(String key) {
        return httpRequestHeader.get(key);
    }

    public String getCookie(String key) {
        return null;
    }

    public String getResourcePath() {
        return httpRequestHeader.getResourcePath();
    }

    public String getParameter(String key) {
        String method = httpRequestHeader.getMethod();
        if ("GET".equals(method)) {
            return httpRequestHeader.getParameter(key);
        }
        return httpRequestBody.getParameter(key);
    }
}
