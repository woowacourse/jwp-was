package http.request;

import http.Cookie;
import http.HttpHeader;
import http.HttpVersion;
import http.request.exception.NotFoundHttpRequestHeader;
import http.response.HttpResponse;
import session.Session;
import session.SessionRepository;

import java.util.Map;

public class HttpRequest {
    private static final String POINT = ".";
    private static final String COOKIE = "Cookie";
    private static final String PATH = "Path";
    private HttpRequestStartLine httpRequestStartLine;
    private HttpHeader httpHeader;
    private HttpBody httpBody;
    private Cookie cookie;

    public HttpRequest(HttpRequestStartLine httpRequestStartLine, HttpHeader httpHeader, HttpBody httpBody) {
        this.httpRequestStartLine = httpRequestStartLine;
        this.httpHeader = httpHeader;
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

    public Session getSession(HttpResponse response) {
        String sessionId = cookie.getCookieValue(Cookie.JSESSIONID);
        Session session = SessionRepository.getSession(sessionId);
        if(sessionId == null) {
            response.addCookie(Cookie.JSESSIONID, session.getId());
            response.addCookie(PATH, "/");
        }
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
        return httpHeader.getHeader(key);
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
}
