package http.request;

import http.session.HttpSession;

public class HttpRequest {
    private final HttpStartLine httpStartLine;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpCookie httpCookie;
    private HttpSession httpSession;
    private final HttpRequestBody httpRequestBody;

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

    public Object getSessionAttribute(final String name) {
        return httpSession.getAttribute(name);
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

    public String getSessionId() {
        return httpSession.getId().toString();
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
