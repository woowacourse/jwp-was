package http.request;

import java.util.Map;

import http.session.HttpCookie;
import http.session.SessionManager;

public class HttpRequest {

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private SessionManager sessionManager;
    private HttpCookie httpCookie = new HttpCookie();

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    public boolean hasCookie() {
        return !httpCookie.isEmpty();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getCookie(String key) {
        return httpCookie.getCookie(key);
    }

    public Map<String, String> getCookies() {
        return httpCookie.getCookies();
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
