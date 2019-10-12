package http.request;

import http.HttpHeaders;
import http.HttpVersion;
import http.session.HttpCookie;
import http.session.HttpSession;
import http.session.SessionManager;

import static http.HttpHeaders.COOKIE;
import static http.request.HttpMethod.GET;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private String body;
    private QueryParams queryParams;
    private SessionManager sessionManager;

    // TODO: 2019-10-11 builder 패턴 적용 고려
    HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.queryParams = splitQueryParams();
    }

    private QueryParams splitQueryParams() {
        return GET.equals(requestLine.getHttpMethod())
                ? QueryParams.of(requestLine.getQueryParams())
                : QueryParams.of(body);
    }

    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getPath() {
        HttpUri uri = requestLine.getUri();
        return uri.getPath();
    }

    public HttpVersion getVersion() {
        return requestLine.getVersion();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public QueryParams getQueryParams() {
        return queryParams;
    }

    public String getBody() {
        return body;
    }

    // TODO: 2019-10-08 httpCookie 미리 만들어서 가지고 있도록 구현
    public HttpCookie getCookies() {
        String cookieString = headers.getHeader(COOKIE);
        return HttpCookie.parse(cookieString);
    }

    public void bindTo(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public HttpSession getSession() {
        HttpCookie cookies = getCookies();
        String jSessionId = cookies.getCookieValue("JSESSIONID");

        if (sessionManager.getHttpSession(jSessionId) == null) {
            return sessionManager.getNewHttpSession();
        }
        return sessionManager.getHttpSession(jSessionId);
    }

    @Override
    public String toString() {
        return requestLine + "\n" + headers + "\n";
    }
}
