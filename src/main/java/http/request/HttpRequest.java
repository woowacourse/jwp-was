package http.request;

import http.common.Cookie;
import http.common.Cookies;
import http.common.HttpHeader;
import http.common.HttpSession;
import http.common.HttpSessionManager;
import http.common.HttpVersion;

import static http.common.HttpSessionManager.JSESSIONID;

public class HttpRequest {

    private RequestLine requestLine;
    private HttpHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(final RequestLine requestLine, final HttpHeader requestHeader, final RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public HttpVersion getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String findHeader(String name) {
        return requestHeader.getHeader(name);
    }

    public HttpUri getUri() {
        return requestLine.getHttpUri();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getQuery() {
        return requestLine.getQuery();
    }

    public String findUriParam(String name) {
        return requestLine.getQueryParam().get(name);
    }

    public String findBodyParam(String name) {
        return requestBody.findParam(name);
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public String findPathPrefix() {
        return requestLine.findPathPrefix();
    }

    public String getClassPath() {
        return requestLine.getClassPath();
    }

    public String getContentType() {
        return requestHeader.getContentType();
    }

    public Cookies getCookies() {
        return requestHeader.getCookies();
    }

    public HttpSession getSession() {
        Cookies cookies = getCookies();
        String sessionId = cookies.getCookie(JSESSIONID);
        HttpSession httpSession = HttpSessionManager.getSession(sessionId);
        cookies.addCookie(new Cookie(JSESSIONID, httpSession.getId()));
        return httpSession;
    }

    public String getCookie(final String name) {
        return getCookies().getCookie(name);
    }

    public static final class HttpRequestBuilder {
        private RequestLine requestLine;
        private HttpHeader requestHeader;
        private RequestBody requestBody;

        private HttpRequestBuilder() {
        }

        public static HttpRequestBuilder builder() {
            return new HttpRequestBuilder();
        }

        public HttpRequestBuilder withRequestLine(RequestLine requestLine) {
            this.requestLine = requestLine;
            return this;
        }

        public HttpRequestBuilder withRequestHeader(HttpHeader requestHeader) {
            this.requestHeader = requestHeader;
            return this;
        }

        public HttpRequestBuilder withRequestBody(RequestBody requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(requestLine, requestHeader, requestBody);
        }
    }
}
