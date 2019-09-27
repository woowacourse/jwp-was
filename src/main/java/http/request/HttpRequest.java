package http.request;

import http.common.HttpHeader;
import http.common.HttpVersion;
import http.common.URL;
import http.request.exception.InvalidHttpRequestException;
import http.session.Session;
import http.session.SessionRepository;

public class HttpRequest {
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
        if (requestLine == null || httpHeader == null || body == null) {
            throw new InvalidHttpRequestException();
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
        String sessionId = httpHeader.getSessionId();
        return SessionRepository.getInstance().getSession(sessionId);
    }
}
