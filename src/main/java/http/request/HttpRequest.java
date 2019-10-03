package http.request;

import http.common.HttpCookie;
import http.common.HttpHeader;
import http.common.HttpSession;
import http.common.SessionPool;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final String COOKIE_HEADER = "Cookie";
    private static final String COOKIE_DELIMITER = "; ";
    private final RequestLine requestLine;
    private final HttpRequestParams httpRequestParams;
    private final HttpHeader httpHeader;
    private final MessageBody messageBody;
    private List<HttpCookie> cookies;
    private String jSessionId;

    public HttpRequest(final RequestLine requestLine,
                       final HttpRequestParams httpRequestParams,
                       final HttpHeader httpHeader,
                       final MessageBody messageBody) {
        this.requestLine = requestLine;
        this.httpRequestParams = httpRequestParams;
        this.httpHeader = httpHeader;
        this.messageBody = messageBody;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpRequestParams getHttpRequestParams() {
        return httpRequestParams;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public String getJSessionId() {
        return jSessionId;
    }

    public HttpSession getHttpSession() {
        HttpCookie jSessionId = getCookies().stream()
                .filter(cookie -> SessionPool.SESSION_ID.equals(cookie.getName()))
                .findFirst()
                .orElse(null);

        HttpSession httpSession;
        if (jSessionId == null) {
            httpSession = SessionPool.getSession();
        } else {
            httpSession = SessionPool.getSession(UUID.fromString(jSessionId.getValue()));
        }

        this.jSessionId = httpSession.getSessionId();
        return httpSession;
    }

    public List<HttpCookie> getCookies() {
        if (cookies == null) {
            cookies = cookieParse();
        }

        return cookies;
    }

    private List<HttpCookie> cookieParse() {
        String rawCookies = httpHeader.get(COOKIE_HEADER);
        if (rawCookies == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(rawCookies.split(COOKIE_DELIMITER))
                .map(rawCookie -> rawCookie.split("="))
                .map(splitedCookie -> HttpCookie.builder(splitedCookie[0], splitedCookie[1]).build())
                .collect(Collectors.toList());
    }
}