package http.request;

import http.common.HttpMethod;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    private HttpRequest(RequestLine requestLine,
                        RequestHeader requestHeader,
                        RequestBody requestBody) {
        this.requestLine = requestLine;
            this.requestHeader = requestHeader;
            this.requestBody = requestBody;
    }

    public static HttpRequest of(RequestLine requestLine,
                                 RequestHeader requestHeader,
                                 RequestBody requestBody) {
        return new HttpRequest(requestLine,
                requestHeader,
                requestBody);
    }

    public String getUrl() {
        String path = requestLine.getPath();
        String protocol = requestLine.getProtocol();
        String host = requestHeader.getHeader("Host");

        return String.format("%s://%s%s", protocol, host, path);
    }

    public String getQuery(String key) {
        return requestLine.getQuery().get(key);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public boolean isGet() {
        return requestLine.getMethod().equals(HttpMethod.GET);
    }

    public boolean isPost() {
        return requestLine.getMethod().equals(HttpMethod.POST);
    }

    public String getHeader(String headerKey) {
        return requestHeader.getHeader(headerKey);
    }

    public String getEntityValue(String entityKey) {
        return requestBody.getEntityValue(entityKey);
    }
}
