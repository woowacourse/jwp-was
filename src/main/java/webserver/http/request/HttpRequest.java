package webserver.http.request;

import webserver.http.Cookie;
import webserver.http.Cookies;
import webserver.http.HttpSession;
import webserver.http.HttpSessionManager;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders headers;
    private final Parameters parameters;
    private final Cookies cookies;
    private final HttpSessionManager sessionManager;
    private HttpSession httpSession;

    HttpRequest(final RequestLine requestLine, final RequestHeaders headers, final Parameters parameters, final Cookies cookies) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.parameters = parameters;
        this.cookies = cookies;
        sessionManager = HttpSessionManager.getInstance();
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
        private RequestHeaders headers;
        private Parameters parameters;
        private Cookies cookies;

        HttpRequestBuilder() {
        }

        HttpRequestBuilder requestLine(RequestLine requestLine) {
            this.requestLine = requestLine;
            return this;
        }

        HttpRequestBuilder headers(RequestHeaders headers) {
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