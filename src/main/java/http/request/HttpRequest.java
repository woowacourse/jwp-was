package http.request;

import http.common.HttpHeader;
import http.common.HttpVersion;
import http.common.URL;
import http.request.exception.InvalidHttpRequestException;
import http.session.Session;
import http.session.SessionRepository;

import java.util.Objects;

public class HttpRequest {
    public static final String SESSIONID = "SessionID";

    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final RequestBody body;

    public HttpRequest(RequestLine requestLine, HttpHeader httpHeader, RequestBody body) {
        checkValidHttpRequest(requestLine, httpHeader, body);
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
        this.body = body;
    }

    private void checkValidHttpRequest(RequestLine requestLine, HttpHeader httpHeader, RequestBody body) {
        try {
            Objects.requireNonNull(requestLine, "요청 라인이 NULL 입니다.");
            Objects.requireNonNull(httpHeader, "HTTP 요청 헤더가 NULL 입니다");
            Objects.requireNonNull(body, "HTTP 요청 바디가 NULL 입니다.");
        } catch (NullPointerException e) {
            throw new InvalidHttpRequestException(e);
        }
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public String getPath() {
        URL url = requestLine.getUrl();
        return url.getPath();
    }

    public String getQueryParameter(String key) {
        return requestLine.getQueryParameter(key);
    }

    public String getFormDataParameter(String key) {
        return body.getFormData(key);
    }

    public RequestMethod getMethod() {
        return requestLine.getMethod();
    }

    public HttpVersion getHttpVersion() {
        return requestLine.getVersion();
    }

    public Session getSession() {
        String sessionId = httpHeader.getCookieAttribute(SESSIONID);
        return SessionRepository.getInstance().getSession(sessionId);
    }
}