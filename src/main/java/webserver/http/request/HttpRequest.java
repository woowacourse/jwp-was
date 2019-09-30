package webserver.http.request;

import webserver.http.*;

import java.util.Objects;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final Parameters parameters;
    private final Cookies cookies;
    private final SessionManager sessionManager;
    private HttpSession httpSession;

    HttpRequest(final RequestLine requestLine, final HttpHeaders headers, final Parameters parameters, final Cookies cookies, final SessionManager sessionManager) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.parameters = parameters;
        this.cookies = cookies;
        this.sessionManager = sessionManager;
        this.httpSession = sessionManager.getSession(cookies.getSessionId()).orElse(null);
    }

    public int sizeOfParameters() {
        return parameters.size();
    }

    public HttpSession getSession() {
        if(Objects.isNull(httpSession)){
            httpSession = sessionManager.createSession();
        }
        return httpSession;
    }

    public String getSessionId(){
        return httpSession.getId();
    }

    public boolean notHasSession() {
        return !cookies.contains(Cookies.JSESSIONID);
    }

    public boolean isCreatedSession() {
        return cookies.getSessionId() == null && httpSession != null;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public Cookie getCookie(final String name) {
        return cookies.get(name);
    }

    public Cookies getCookies() {
        return cookies;
    }

    public String getHeader(final String name) {
        return headers.get(name);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpVersion getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getParameter(final String key) {
        return parameters.get(key);
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public static final class HttpRequestBuilder {
        private RequestLine requestLine;
        private HttpHeaders headers;
        private Parameters parameters;
        private Cookies cookies;
        private SessionManager sessionManager;

        HttpRequestBuilder() {
        }

        HttpRequestBuilder requestLine(RequestLine requestLine) {
            this.requestLine = requestLine;
            return this;
        }

        HttpRequestBuilder headers(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        HttpRequestBuilder parameters(Parameters parameters) {
            this.parameters = parameters;
            return this;
        }

        HttpRequestBuilder cookies(Cookies cookies) {
            this.cookies = cookies;
            return this;
        }

        HttpRequestBuilder sessionManager(SessionManager sessionManager) {
            this.sessionManager = sessionManager;
            return this;
        }

        HttpRequest build() {
            return new HttpRequest(requestLine, headers, parameters, cookies, sessionManager);
        }
    }

}