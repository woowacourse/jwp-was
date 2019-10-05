package webserver.http.request;

import webserver.http.Cookie;
import webserver.http.HttpMethod;
import webserver.http.HttpSession;
import webserver.http.HttpSessionManager;
import webserver.http.exception.NotFoundRequestCookieException;

import java.util.Map;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private HttpSessionManager sessionManager;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, HttpSessionManager sessionManager) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.sessionManager = sessionManager;
    }

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody, HttpSessionManager sessionManager) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.sessionManager = sessionManager;
    }

    public boolean isSameHttpMethod(HttpMethod httpMethod) {
        return requestLine.isSameHttpMethod(httpMethod);
    }

    public String getResource() {
        return requestLine.getResource();
    }

    public String getPath() {
        if (requestLine.getPath().equals("/")) {
            return "/index.html";
        }
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

    public HttpSession getHttpSession() {
        if (requestHeader.isCookieExist()) {
            return sessionManager.getSession(getCookieValue("JSESSIONID"));
        }
        HttpSession httpSession = new HttpSession();
        sessionManager.setSession(httpSession.getJSESSIONID(), httpSession);
        return httpSession;
    }

    private Cookie getCookie(String key) {
        return requestHeader.getCookies().stream()
                .filter(cookie -> cookie.getName().equals(key))
                .findFirst()
                .orElseThrow(NotFoundRequestCookieException::new)
                ;
    }

    private String getCookieValue(String key) {
        return getCookie(key).getValue();
    }

    public String getHttpVersion() {
        return requestLine.getVersion();
    }

    public void addHeader(String key, String value) {
        requestHeader.add(key, value);
    }
}