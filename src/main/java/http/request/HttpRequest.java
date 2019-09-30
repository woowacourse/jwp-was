package http.request;

import http.common.Cookies;
import http.common.HttpHeader;
import http.common.HttpVersion;

public class HttpRequest {

    private static final String ACCEPT_DELIMITER = ",";
    private static final String ACCEPT = "Accept";
    private RequestLine requestLine;
    private HttpHeader requestHeader;
    private Cookies cookies;
    private RequestBody requestBody;

    public HttpRequest(final RequestLine requestLine, final HttpHeader requestHeader, final Cookies cookies, final RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.cookies = cookies;
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

    public boolean isLogined() {
        return cookies.isLogined();
    }

    public String getContentType() {
        return requestHeader.getHeader(ACCEPT).split(ACCEPT_DELIMITER)[0];
    }

    public static final class HttpRequestBuilder {
        private RequestLine requestLine;
        private HttpHeader requestHeader;
        private Cookies cookies = new Cookies();
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

        public HttpRequestBuilder withCookies(Cookies cookies) {
            this.cookies = cookies;
            return this;
        }

        public HttpRequestBuilder withRequestBody(RequestBody requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(requestLine, requestHeader, cookies, requestBody);
        }
    }
}
