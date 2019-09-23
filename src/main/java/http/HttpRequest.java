package http;

import java.util.Map;

public class HttpRequest {
    private static final String ROOT_URL = "/";
    private static final String INDEX_HTML = "/index.html";

    private final HttpRequestHeader httpRequestHeader;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequest(HttpRequestHeader header) {
        this(header, new HttpRequestBody(""));
    }

    public String extractUrl() {
        return validate(httpRequestHeader.getUrl());
    }

    private String validate(String url) {
        if (ROOT_URL.equals(url)) {
            return INDEX_HTML;
        }
        return url;
    }

    public String getHeader(String key) {
        return httpRequestHeader.get(key);
    }

    public String getMethod() {
        return httpRequestHeader.getMethod();
    }

    public String getUrl() {
        return httpRequestHeader.getUrl();
    }

    public String getVersion() {
        return httpRequestHeader.getVersion();
    }

    public Map<String, String> extractFormData() {
        return httpRequestBody.getBody();
    }
}
