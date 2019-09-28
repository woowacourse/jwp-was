package http.request;

import http.HttpCookie;
import http.HttpHeaders;
import http.HttpMimeType;
import http.HttpVersion;
import http.session.HttpSession;
import http.session.HttpSessionTable;
import http.session.UuidStrategy;
import utils.ExtensionParser;

import static http.HttpCookie.SESSION_ID_KEY;
import static http.HttpHeaders.*;
import static http.HttpMimeType.X_WWW_FORM_URLENCODED;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private String body;
    private QueryParams queryParams;
    private HttpCookie cookie;
    private HttpSession session;

    HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.queryParams = QueryParams.of(getParams());
        this.cookie = HttpCookie.of(headers.getHeader(COOKIE));
        this.session =
                HttpSessionTable.getSession(cookie.get(SESSION_ID_KEY), new UuidStrategy());
    }

    private String getParams() {
        if (HttpMethod.GET.match(requestLine.getMethod())) {
            return requestLine.getUri().getQueryParams();
        }
        if (existQueryParamsInBody()) {
            return body;
        }
        return null;
    }

    private boolean existQueryParamsInBody() {
        return X_WWW_FORM_URLENCODED.match(headers.getHeader(CONTENT_TYPE));
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public HttpUri getUri() {
        return requestLine.getUri();
    }

    public HttpVersion getVersion() {
        return requestLine.getVersion();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getParam(String key) {
        return queryParams.getParam(key);
    }

    public HttpMimeType getMimeType() {
        String accept = headers.getHeader(ACCEPT);
        String extension = ExtensionParser.parse(requestLine.getUri().getPath());
        return HttpMimeType.getMimeTypeFrom(accept, extension);
    }

    public String getBody() {
        return body;
    }

    public boolean matchCookie(String key, String value) {
        String cookieValue = cookie.get(key);
        if (cookie == null || cookieValue == null) {
            return false;
        }
        return cookieValue.equals(value);
    }

    @Override
    public String toString() {
        return requestLine + "\n" + headers + "\n";
    }
}
