package server.http.request;

import was.http.context.BasicSessionHandler;
import was.http.context.Session;
import was.http.context.SessionHandler;

import java.util.Map;
import java.util.UUID;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final Map<String, String> headers;
    private final Map<String, String> body;
    private final Map<String, String> cookies;
    private UUID sessionId;

    public HttpRequest(final HttpRequestLine requestLine, Map<String, String> headers, final Map<String, String> cookies, final Map<String, String> body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeader(final String key) {
        return headers.get(key);
    }

    public String getParam(final String key) {
        return requestLine.getQueryParam(key);
    }

    public String getBody(final String key) {
        return body.get(key);
    }

    public String getCookie(final String key) { return cookies.get(key); }

    public Session getSession() {
        SessionHandler sessionHandler = BasicSessionHandler.getInstance();
        if (cookies.containsKey("SESSIONID")) {
            UUID sessionIdFromRequest = UUID.fromString(cookies.get("SESSIONID"));
            if (sessionHandler.hasSession(sessionIdFromRequest)) {
                this.sessionId = sessionIdFromRequest;
                return sessionHandler.getSession(sessionIdFromRequest);
            }
        }
        this.sessionId = UUID.randomUUID();
        return sessionHandler.addNewSession(sessionId);
    }

    public UUID getSessionId() {
        return sessionId;
    }
}