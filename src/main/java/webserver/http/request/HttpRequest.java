package webserver.http.request;

import webserver.http.*;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final Parameters parameters;
    private final Cookies cookies;
    private final HttpSessionManager sessionManager;
    private HttpSession httpSession;

    HttpRequest(final RequestLine requestLine, final HttpHeaders headers, final Parameters parameters, final Cookies cookies) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.parameters = parameters;
        this.cookies = cookies;
        sessionManager = HttpSessionManager.getInstance(); // todo di
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

    public int sizeOfParameters() {
        return parameters.size();
    }

    public HttpSession getSession() {
        return httpSession == null
                ? this.httpSession = createSession()
                : httpSession;
    }

    private HttpSession createSession() {
        final Cookie cookie = cookies.get(Cookies.JSESSIONID);
        return cookie == null
                ? sessionManager.getSession()
                : sessionManager.getSession(cookie.getValue());
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public static final class HttpRequestBuilder {
        private RequestLine requestLine;
        private HttpHeaders headers;
        private Parameters parameters;
        private Cookies cookies;

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

        HttpRequest build() {
            return new HttpRequest(requestLine, headers, parameters, cookies);
        }
    }

}