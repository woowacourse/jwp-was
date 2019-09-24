package http.request;

import http.common.HttpMethod;
import http.common.Parameters;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private Parameters parameters;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, Parameters parameters) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.parameters = parameters;
    }

    public static HttpRequest of(RequestLine requestLine, RequestHeader requestHeader, Parameters parameters) {
        return new HttpRequest(requestLine, requestHeader, parameters);
    }

    public String getUrl() {
        String path = requestLine.getPath();
        String protocol = requestLine.getProtocol();
        String host = requestHeader.getHeader("Host");

        return String.format("%s://%s%s", protocol, host, path);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
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

    public String getParameter(String key) {
        return parameters.getParameter(key);
    }
}
