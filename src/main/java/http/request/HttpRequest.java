package http.request;

import java.util.Objects;

import http.Cookies;
import http.HttpSession;
import http.HttpSessionStorage;
import type.method.MethodType;

public class HttpRequest {

    private static final String HEADER_ACCEPT = "Accept";
    private static final String COMMA = ",";

    private final HttpRequestLine httpRequestLine;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpRequestBody httpRequestBody;
    private final HttpSession httpSession;

    public HttpRequest(final HttpRequestLine httpRequestLine, final HttpRequestHeader httpRequestHeader, final HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
        this.httpSession = initSession();
    }

    private HttpSession initSession() {
        Cookies cookies = this.httpRequestHeader.getCookies();
        if (Objects.isNull(cookies.getSessionId())) {
            return new HttpSession();
        }
        return HttpSessionStorage.getHttpSessionById(cookies.getSessionId());
    }

    public MethodType getMethod() {
        return this.httpRequestLine.getMethod();
    }

    public String getUrl() {
        return this.httpRequestLine.getUrl();
    }

    public String getHttpRequestHeaderByName(final String name) {
        return this.httpRequestHeader.getValue(name);
    }

    public String getHttpRequestParamsByName(final String name) {
        return this.httpRequestLine.getValue(name);
    }

    public String getHttpRequestBodyByName(final String name) {
        return this.httpRequestBody.getValue(name);
    }

    public String getContentType() {
        return this.httpRequestHeader.getValue(HEADER_ACCEPT).split(COMMA)[0];
    }

    public Cookies getCookie() {
        return this.httpRequestHeader.getCookies();
    }

    public String getSessionId() {
        return this.httpSession.getId();
    }

    public HttpSession getHttpSession() {
        return this.httpSession;
    }
}
