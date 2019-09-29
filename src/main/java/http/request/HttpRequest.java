package http.request;

import http.request.support.HttpMethod;
import http.session.HttpSession;

public class HttpRequest {
    private final HttpStartLine httpStartLine;
    private final HttpHeader httpHeader;
    private final HttpCookie httpCookie;
    private final HttpSession httpSession;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(HttpStartLine httpStartLine,
                       HttpHeader httpHeader,
                       HttpCookie httpCookie,
                       HttpSession httpSession,
                       HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpHeader = httpHeader;
        this.httpCookie = httpCookie;
        this.httpSession = httpSession;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequest(HttpStartLine httpStartLine,
                       HttpHeader httpHeader,
                       HttpCookie httpCookie,
                       HttpSession httpSession) {
        this(httpStartLine, httpHeader, httpCookie, httpSession, new HttpRequestBody(""));
    }

    public void addSessionAttribute(final String name, final Object attribute) {
        httpSession.setAttribute(name, attribute);
    }

    public void removeSessionAttribute(final String name) {
        httpSession.removeAttribute(name);
    }

    public void invalidateSession() {
        httpSession.invalidate();
    }

    public Object getSessionAttribute(final String name) {
        return httpSession.getAttribute(name);
    }

    public HttpMethod getMethod() {
        return httpStartLine.getMethod();
    }

    public String getHeader(String key) {
        return httpHeader.getHeader(key);
    }

    public String getCookie(String key) {
        return httpCookie.getValue(key);
    }

    public String getSessionId() {
        return httpSession.getId().toString();
    }

    public String getResourcePath() {
        return httpStartLine.getResourcePath();
    }

    public String getParameter(final String key) {
        HttpMethod method = httpStartLine.getMethod();
        if (HttpMethod.GET.equals(method)) {
            return httpStartLine.getParameter(key);
        }
        return httpRequestBody.getParameter(key);
    }
}
