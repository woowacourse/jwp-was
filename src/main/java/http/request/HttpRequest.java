package http.request;

import http.common.HttpCookie;
import http.common.HttpHeader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final String COOKIE_HEADER = "Cookie";
    private static final String COOKIE_DELIMITER = "; ";
    private final RequestLine requestLine;
    private final HttpRequestParams httpRequestParams;
    private final HttpHeader httpHeader;
    private final HttpRequestBody httpRequestBody;
    private List<HttpCookie> cookies;

    public HttpRequest(final RequestLine requestLine,
                       final HttpRequestParams httpRequestParams,
                       final HttpHeader httpHeader,
                       final HttpRequestBody httpRequestBody) {
        this.requestLine = requestLine;
        this.httpRequestParams = httpRequestParams;
        this.httpHeader = httpHeader;
        this.httpRequestBody = httpRequestBody;
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

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
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