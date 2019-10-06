package http.model;

import http.supoort.SessionManager;

import java.util.regex.Pattern;

import static com.google.common.net.HttpHeaders.COOKIE;

public class HttpRequest {
    private static final String JSESSIONID = "JSESSIONID";
    private final RequestLine requestLine;
    private final HttpParameters parameters;
    private final HttpHeaders httpHeaders;

    public HttpRequest(RequestLine requestLine, HttpParameters parameters, HttpHeaders httpHeaders) {
        this.requestLine = requestLine;
        this.parameters = parameters;
        this.httpHeaders = httpHeaders;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getParameter(String key) {
        return parameters.getParameter(key);
    }

    public HttpHeaders getHeaders() {
        return httpHeaders;
    }

    public HttpSession getHttpSession() {
        Cookie cookie = new Cookie(getHeader(COOKIE));
        String sessionId = cookie.getCookieValue(JSESSIONID);
        if (sessionId == null) {
            return SessionManager.createSession();
        }
        return SessionManager.getSession(sessionId);
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public boolean match(Pattern pattern, HttpMethod method) {
        return requestLine.getHttpUri().match(pattern) && requestLine.getHttpMethod().match(method);
    }
}