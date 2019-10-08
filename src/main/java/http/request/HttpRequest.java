package http.request;

import http.Cookie;
import http.HttpRequestHeader;
import http.HttpVersion;
import http.request.exception.NotFoundHttpRequestHeader;
import session.Session;
import session.SessionRepository;

import java.util.Map;

import static http.Cookie.JSESSIONID;

public class HttpRequest {
    private static final String POINT = ".";
    private static final String COOKIE = "Cookie";
    private static final String PATH = "Path";
    private HttpRequestStartLine httpRequestStartLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpBody httpBody;
    private Cookie cookie;

    public HttpRequest(HttpRequestStartLine httpRequestStartLine, HttpRequestHeader httpRequestHeader, HttpBody httpBody) {
        this.httpRequestStartLine = httpRequestStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpBody = httpBody;
        this.cookie = createCookie();
    }

    public Map<String, String> convertBodyToMap() {
        return httpBody.convertBodyToMap();
    }

    public boolean hasParameters() {
        return httpRequestStartLine.hasParameters();
    }

    public boolean hasBody() {
        return !httpBody.equals(HttpBody.EMPTY_BODY);
    }

    public boolean isFileRequest() {
        return getPath().contains(POINT);
    }

    public Session getSession() {
        String sessionId = cookie.getCookieValue(JSESSIONID);
        Session session = SessionRepository.getSession(sessionId);

        cookie.addCookie(JSESSIONID, session.getId());
        cookie.addCookie(PATH, "/");

        return session;
    }

    private Cookie createCookie() {
        Cookie cookie = new Cookie();
        try {
            cookie.parse(getHeader(COOKIE));
            return cookie;
        } catch (NotFoundHttpRequestHeader e) {
            return cookie;
        }
    }

    public HttpMethodType getHttpMethod() {
        return httpRequestStartLine.getHttpMethodType();
    }

    public String getPath() {
        return httpRequestStartLine.getPath();
    }

    public String getHeader(String key) {
        return httpRequestHeader.getHeader(key);
    }

    public String getParameter(String key) {
        return httpRequestStartLine.getParameter(key);
    }

    public HttpBody getBody() {
        return httpBody;
    }

    public HttpVersion getVersion() {
        return httpRequestStartLine.getHttpVersion();
    }

    public Cookie getCookie() {
        return cookie;
    }
}
