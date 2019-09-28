package http.request;

import http.session.HttpSession;

public class HttpRequest {
    private final HttpStartLine httpStartLine;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpCookie httpCookie;
    private HttpSession httpSession;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader,
                       HttpCookie httpCookie, HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpCookie = httpCookie;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader header, HttpCookie httpCookie) {
        this(httpStartLine, header, httpCookie, new HttpRequestBody(""));
    }

    public HttpRequest(HttpStartLine httpStartLine,
                       HttpRequestHeader httpRequestHeader,
                       HttpCookie httpCookie,
                       HttpSession httpSession,
                       HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpCookie = httpCookie;
        this.httpSession = httpSession;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequest(HttpStartLine httpStartLine,
                       HttpRequestHeader httpRequestHeader,
                       HttpCookie httpCookie,
                       HttpSession httpSession) {
        this(httpStartLine, httpRequestHeader, httpCookie, httpSession, new HttpRequestBody(""));
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

    public void getSessionAttribute(final String name) {
        httpSession.getAttribute(name);
    }

    public String getMethod() {
        return httpStartLine.getMethod();
    }

    public String getHeader(String key) {
        return httpRequestHeader.getHeader(key);
    }

    public String getCookie(String key) {
        return httpCookie.getValue(key);
    }

    public String getResourcePath() {
        return httpStartLine.getResourcePath();
    }

    public String getParameter(String key) {
        String method = httpStartLine.getMethod();
        if ("GET".equals(method)) {
            return httpStartLine.getParameter(key);
        }
        return httpRequestBody.getParameter(key);
    }
}
