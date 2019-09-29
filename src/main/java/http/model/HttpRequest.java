package http.model;

import http.supoort.SessionManager;

public class HttpRequest {
    private static final String COOKIE = "Cookie";
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

    public HttpParameters getParameters() {
        return parameters;
    }

    public HttpHeaders getHeaders() {
        return httpHeaders;
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public HttpSession getHttpSession() {
        String sessionId = getHeader(COOKIE);
        if (sessionId == null) {
            return SessionManager.createSession();
        }
        return SessionManager.getSession(sessionId);
    }
}
