package http.request;

import java.util.Map;

public class Request {
    private static final String ROOT_URL = "/";
    private static final String INDEX_HTML = "/index.html";

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public Request(RequestLine requestLine, RequestHeader requestHeader) {
        this(requestLine, requestHeader, new RequestBody(""));
    }

    public String extractUrl() {
        return requestLine.getUrl();
    }

    public String extractMethod() {
        return requestLine.getMethod();
    }

    private String validate(String url) {
        if (url.equals(ROOT_URL)) {
            return INDEX_HTML;
        }
        return url;
    }

    public String extractHeader(String key) {
        return requestHeader.get(key);
    }

    public Map<String, String> extractQueryParameter() {
        return requestHeader.extractQueryParameter();
    }

    public Map<String, String> extractFormData() {
        return requestBody.getBody();
    }
}
