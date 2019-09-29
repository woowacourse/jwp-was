package webserver.http.request;

import webserver.http.request.core.*;
import webserver.http.session.Cookie;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionManager;

public class HttpRequest {
    private static final String COOKIE = "Cookie";
    private static final String JSESSIONID = "JSESSIONID";

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestData requestData;
    private Cookie cookie;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        hasCookie();
    }

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestData requestData) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestData = requestData;
        hasCookie();
    }

    private void hasCookie() {
        if (requestHeader.hasCookie()) {
            createCookie(requestHeader.getHeadersKey(COOKIE));
        }
    }

    private void createCookie(String cookie) {
        this.cookie = new Cookie(cookie);
    }

    public RequestMethod getRequestMethod() {
        return requestLine.getRequestMethod();
    }

    public RequestPath getRequestPath() {
        return requestLine.getRequestPath();
    }

    public String getBodyValue(String key) {
        return requestData.getValue(key);
    }

    public HttpSession getSession() {
        if (cookie == null) {
            return null;
        }
        String uuid = this.cookie.getCookies(JSESSIONID);
        return SessionManager.getSession(uuid);
    }
}
