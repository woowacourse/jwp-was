package http.request;

import http.common.Cookie;
import http.common.HttpMethod;
import http.common.Parameters;

import java.util.List;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private Parameters parameters;
    private List<Cookie> cookies;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, Parameters parameters, List<Cookie> cookies) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.parameters = parameters;
        this.cookies = cookies;
    }

    public static HttpRequest of(RequestLine requestLine, RequestHeader requestHeader,
                                 Parameters parameters, List<Cookie> cookies) {
        return new HttpRequest(requestLine, requestHeader, parameters, cookies);
    }

    public boolean isGet() {
        return requestLine.getMethod().equals(HttpMethod.GET);
    }

    public boolean isPost() {
        return requestLine.getMethod().equals(HttpMethod.POST);
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

    public String getHeader(String headerKey) {
        return requestHeader.getHeader(headerKey);
    }

    public String getParameter(String key) {
        return parameters.getParameter(key);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}
