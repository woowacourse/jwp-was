package http.request;

import http.HttpMethod;
import http.MediaType;
import http.session.HttpSession;
import http.session.HttpSessionManager;

public class HttpRequest {
    public static final String SESSION_ID = "JSESSIONID";
    private static final String COOKIE = "Cookie";

    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpCookie httpCookie;
    private HttpSession httpSession;
    private QueryParameter queryParameter;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpRequestHeader httpRequestHeader,
                       QueryParameter queryParameter, HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpCookie = HttpCookie.of(findCookie());
        checkSession();
        this.queryParameter = queryParameter;
        this.httpRequestBody = httpRequestBody;
    }

    private void checkSession() {
        if (httpCookie.contains(SESSION_ID)) {
            this.httpSession = HttpSessionManager.getInstance().getSession(httpCookie.getCookie(SESSION_ID));
        }
    }

    private String findCookie() {
        if (httpRequestHeader.isContainKey(COOKIE)) {
            return this.httpRequestHeader.getHeader(COOKIE);
        }
        return "";
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public boolean isContainExtension() {
        return MediaType.isContain(httpRequestLine.getPath());
    }

    public byte[] getHttpRequestBody() {
        return httpRequestBody.getBody();
    }

    public String getQueryValue(String key) {
        return queryParameter.getValue(key);
    }

    public HttpMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getUri() {
        return httpRequestLine.getPath();
    }

    public String getCookie(String key) {
        return this.httpCookie.getCookie(key);
    }

    public HttpSession getHttpSession() {
        if (httpSession == null) {
            this.httpSession = HttpSessionManager.getInstance().createSession();
        }
        this.httpSession = HttpSessionManager.getInstance().getSession(this.httpSession.getId());
        return httpSession;
    }
}
