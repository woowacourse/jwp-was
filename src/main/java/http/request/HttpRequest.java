package http.request;

import http.HttpHeaders;
import http.HttpVersion;
import http.session.Cookie;
import http.session.HttpSession;
import http.session.SessionManager;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private String body;
    private QueryParams queryParams;
    private Cookie cookie;
    private SessionManager sessionManager;

    // TODO: 2019-10-11 builder 패턴 적용하여 리펙토링
    HttpRequest(HttpRequestLine requestLine, HttpHeaders headers,
                String body, QueryParams queryParams, Cookie cookie) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.queryParams = queryParams;
        this.cookie = cookie;
    }

    public HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getPath() {
        HttpUri uri = requestLine.getPath();
        return uri.getPath();
    }

    public HttpVersion getVersion() {
        return requestLine.getVersion();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public QueryParams getQueryParams() {
        return queryParams;
    }

    public String getBody() {
        return body;
    }

    public void addCookie(String key, String value) {
        cookie.addAttribute(key, value);
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void bindTo(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public HttpSession getSession() {
        String jSessionId = cookie.getAttribute("JSESSIONID");

        return sessionManager.getHttpSession(jSessionId) == null
                ? sessionManager.getNewHttpSession()
                : sessionManager.getHttpSession(jSessionId);
    }

    @Override
    public String toString() {
        return requestLine + "\n" + headers + "\n";
    }
}
