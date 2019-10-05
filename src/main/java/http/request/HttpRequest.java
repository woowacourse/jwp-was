package http.request;

import http.*;

import java.util.Arrays;

import static http.HttpHeader.*;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final HttpHeader header;
    private final HttpBody body;
    private QueryString queryString;

    public HttpRequest(HttpRequestLine requestLine, HttpHeader header, HttpBody body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
        this.queryString = new QueryString();
        addQueryString();
    }

    private void addQueryString() {
        requestLine.getUri().addQueryString(queryString);
        body.addQueryString(header, queryString);
    }

    public static class HttpRequestBuilder {
        private HttpRequestLine requestLine;
        private HttpHeader header;
        private HttpBody body;

        public HttpRequestBuilder() { }

        public HttpRequestBuilder requestLine(HttpRequestLine requestLine) {
            this.requestLine = requestLine;
            return this;
        }

        public HttpRequestBuilder header(HttpHeader header) {
            this.header = header;
            return this;
        }

        public HttpRequestBuilder body(HttpBody body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(requestLine, header, body);
        }
    }

    public HttpSession getSession() {
        String cookie = header.getValue(COOKIE_KEY);
        return Arrays.stream(cookie.split(VALUES_DELIMITER))
                .filter(value -> value.contains(JSESSIONID))
                .findAny()
                .map(id -> {
                    String[] tokens = id.split(KEY_VALUE_DELIMITER);
                    return HttpSessionManager.getSession(tokens[1].trim());
                })
                .orElseGet(HttpSessionManager::createSession)
                ;
    }

    public boolean isStaticRequest() {
        return requestLine.includeFileUri();
    }

    public String getUri() {
        return requestLine.getUri().toString();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getHeaderAttribute(String key) {
        return header.getValue(key);
    }

    public String getQueryParameter(String key) {
        return queryString.getParameter(key);
    }
}
