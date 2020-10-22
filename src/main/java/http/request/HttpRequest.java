package http.request;

import java.util.Map;
import java.util.Objects;

import http.session.HttpCookie;
import http.session.SessionManager;

public class HttpRequest {

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private SessionManager sessionManager;
    private HttpCookie httpCookie;
    private HttpParameters httpParameters;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody,
        HttpCookie cookies, HttpParameters parameters) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.httpCookie = cookies;
        this.httpParameters = parameters;
    }

    public static HttpRequest of(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody,
        Map<String, String> cookies, Map<String, String> parameters) {
        return new HttpRequest(requestLine, requestHeader, requestBody, new HttpCookie(cookies),
            new HttpParameters(parameters));
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

    public String getParameter(String key) {
        return httpParameters.getParameter(key);
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
