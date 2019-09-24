package http.request;

import java.util.Map;

public class Request {
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

    public boolean isGetMethod() {
        return requestLine.isGet();
    }

    public boolean isPostMethod() {
        return requestLine.isPost();
    }

    public String extractUrl() {
        return requestLine.getUrl();
    }

    public String extractHttpVersion(){
        return requestLine.getHttpVersion();
    }

    public String extractHeader(String key) {
        return requestHeader.get(key);
    }

    public Map<String, String> extractQueryParameter() {
        return requestLine.extractQueryParameter();
    }

    public Map<String, String> extractFormData() {
        return requestBody.getBody();
    }
}
