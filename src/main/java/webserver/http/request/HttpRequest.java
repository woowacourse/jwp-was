package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.exception.NoMatchHeaderFieldException;
import webserver.http.request.core.*;
import webserver.session.Cookie;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

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

    public String getBodyValue(String key) {
        return requestData.getValue(key);
    }

    public String getHttpCookieId() {
        return cookie.getCookies("JSESSIONID");
    }

    public RequestMethod getRequestMethod() {
        return requestLine.getRequestMethod();
    }

    public RequestPath getRequestPath() {
        return requestLine.getRequestPath();
    }

    private void hasCookie() {
        if (requestHeader.hasCookie()) {
            createCookie(requestHeader.getHeadersKey("Cookie"));
        }
    }

    private void createCookie(String cookie) {
        this.cookie = new Cookie(cookie);
    }
}
