package http.request;

import http.common.HttpMethod;

public class HttpRequest {
    private RequestStartLine requestStartLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    private HttpRequest(RequestStartLine requestStartLine,
                       RequestHeader requestHeader,
                       RequestBody requestBody) {
            this.requestStartLine = requestStartLine;
            this.requestHeader = requestHeader;
            this.requestBody = requestBody;
    }

    public static HttpRequest of(RequestStartLine requestStartLine,
                                 RequestHeader requestHeader,
                                 RequestBody requestBody) {
        return new HttpRequest(requestStartLine,
                requestHeader,
                requestBody);
    }

    public String getUrl() {
        String path = requestStartLine.getPath();
        String protocol = requestStartLine.getProtocol();
        String host = requestHeader.getHeader("Host");

        return String.format("%s://%s%s", protocol, host, path);
    }

    public String getQuery(String key) {
        return requestStartLine.getQuery().get(key);
    }

    public String getPath() {
        return requestStartLine.getPath();
    }

    public boolean isGet() {
        return requestStartLine.getMethod().equals(HttpMethod.GET);
    }

    public boolean isPost() {
        return requestStartLine.getMethod().equals(HttpMethod.POST);
    }

    public String getHeader(String headerKey) {
        return requestHeader.getHeader(headerKey);
    }

    public String getEntityValue(String entityKey) {
        return requestBody.getEntityValue(entityKey);
    }
}
