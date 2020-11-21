package http.request;

import exception.IllegalRequestException;
import http.ContentType;
import http.HttpHeaders;
import http.session.HttpSession;
import http.session.HttpSessionStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class Request {

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final RequestBody requestBody;
    private Cookies cookies;

    public Request(BufferedReader br) throws IOException, IllegalRequestException {
        this.requestLine = new RequestLine(br);
        this.httpHeaders = new HttpHeaders(br);
        this.requestBody = new RequestBody(br, httpHeaders.getContentLength());
        this.cookies = new Cookies(httpHeaders.getHeader(HttpHeaders.COOKIE));
    }

    public boolean isMethod(RequestMethod requestMethod) {
        return requestMethod == requestLine.getMethod();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getRequestHeaders() {
        return Collections.unmodifiableMap(httpHeaders.getHttpHeaders());
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public QueryParams getQueryParams() {
        return requestLine.getQueryParams();
    }

    public RequestMethod getRequestMethod() {
        return requestLine.getMethod();
    }

    public String getContentType() {
        return ContentType.of(getPath()).getContentType();
    }

    public Cookie getCookie(String key) {
        return cookies.getCookie(key);
    }

    public String getParam(String param) {
        return requestBody.getParam(param);
    }

    public HttpSession getSession() {
        Cookie cookie = cookies.getCookie("JSESSIONID");
        if (cookie == null) {
            return null;
        }
        return HttpSessionStore.getSession(cookies.getCookie("JSESSIONID").getValue());
    }
}
