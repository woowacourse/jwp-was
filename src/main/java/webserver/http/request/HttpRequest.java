package webserver.http.request;

import webserver.http.*;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final Parameters parameters;
    private final Cookies cookies;
    private final SessionManager sessionManager;

    HttpRequest(final RequestLine requestLine, final HttpHeaders headers, final Parameters parameters, final Cookies cookies, final SessionManager sessionManager) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.parameters = parameters;
        this.cookies = cookies;
        this.sessionManager = sessionManager;
    }

    public int sizeOfParameters() {
        return parameters.size();
    }

    private HttpSession createSession() {
        final HttpSession session = sessionManager.getSession();
        cookies.add(new Cookie(Cookies.JSESSIONID, session.getId()));
        return session;
    }

    public HttpSession getSession() {
        final String jSessionId = cookies.getSessionId();
        final HttpSession httpSession = sessionManager.getSession(jSessionId);
        return httpSession == null
                ? createSession()
                : httpSession;
    }

    public boolean hasSession() {
        return cookies.contains(Cookies.JSESSIONID);
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
        return parameters.getParameter(key);
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