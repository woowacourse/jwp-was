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
    private HttpSession httpSession;

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

    public Cookie getCookie(String key) {
        return requestHeader.getCookies().stream()
                .filter(cookie -> cookie.getName().equals(key))
                .findFirst()
                .orElseThrow(NotFoundRequestCookieException::new)
                ;
    }

    public String getCookieValue(String key) {
        return getCookie(key).getValue();
    }

    public HttpSession getHttpSession() {
        if (requestHeader.isCookieExist()) {
            this.httpSession = HttpSessionManager.getSession(getCookieValue("JSESSIONID"));
            return this.httpSession;
        }
        this.httpSession = new HttpSession();
        HttpSessionManager.setSession(httpSession.getJSESSIONID(), httpSession);
        return this.httpSession;
    }

    public String getHttpVersion() {
        return this.requestLine.getVersion();
    }
}