package http.request;

import http.response.Cookies;
import http.session.Session;

import java.util.Map;

public class Request {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;
    private final Cookies cookies;
    private final Session session;

    public Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody,
                   Cookies cookies, Session session) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.cookies = cookies;
        this.session = session;
    }

    public Request(RequestLine requestLine, RequestHeader requestHeader, Cookies cookies, Session session) {
        this(requestLine, requestHeader, new RequestBody(""), cookies, session);
    }

    public Cookies getCookie() {
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
