package webserver.http.request;

import webserver.http.Cookies;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders headers;
    private final Parameters parameters;
    private final Cookies cookies;

    public HttpRequest(final RequestLine requestLine, final RequestHeaders headers, final Parameters parameters, final Cookies cookies) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.parameters = parameters;
        this.cookies = cookies;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getCookie(final String key) {
        return cookies.get(key);
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