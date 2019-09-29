package model.http;

import utils.HttpMethod;

import java.util.Map;

import static webserver.RequestHandler.sessionPool;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
    }

    public boolean isSameHttpMethod(HttpMethod httpMethod) {
        return requestLine.isSameHttpMethod(httpMethod);
    }

    public String getResource() {
        return requestLine.getResource();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getQueryParams() {
        return requestLine.getQueryParams();
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getMethod();
    }

    public String getHeader(String header) {
        return requestHeader.getHeaderValue(header);
    }

    public Map<String, String> getBody() {
        return requestBody.getBody();
    }

    public String getBodyValueBy(String key) {
        return requestBody.getValueBy(key);
    }

    // TODO : 리팩토링 필요
    public HttpSession getHttpSession() {
        if (requestHeader.getCookies() != null) {
            for (Cookie cookie : requestHeader.getCookies()) {
                if ("id".equals(cookie.getName()) && cookie.getValue() != null) {
                    return sessionPool.get(cookie.getValue());
                }
            }
        }
        HttpSession session = new HttpSession();
        sessionPool.put(session.getId(), session);
        return session;
    }

}
