package http.request;

import http.response.CookieCollection;
import http.session.Session;

import java.util.Map;

public class Request {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;
    private final CookieCollection cookies;
    private final Session session;

    public Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody,
                   CookieCollection cookies, Session session) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.cookies = cookies;
        this.session = session;
    }

    public Request(RequestLine requestLine, RequestHeader requestHeader, CookieCollection cookies, Session session) {
        this(requestLine, requestHeader, new RequestBody(""), cookies, session);
    }

    public CookieCollection getCookie() {
        return this.cookies;
    }

    public Session getSession() {
        return this.session;
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

    public String extractHttpVersion() {
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
