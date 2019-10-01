package webserver.http.response.core;

import webserver.http.HttpHeaderField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {
    private static final String CRLF = "\r\n";
    private static final String COLON = ": ";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final int EMPTY_RESPONSE_COOKIES = 0;

    private final Map<HttpHeaderField, String> responseHeaders = new HashMap<>();
    private final List<String> responseCookies = new ArrayList<>();

    public void addHeaders(HttpHeaderField httpHeaderField, String value) {
        responseHeaders.put(httpHeaderField, value);
    }

    public void addHeaders(HttpHeaderField httpHeaderField, ResponseContentType contentType) {
        responseHeaders.put(httpHeaderField, contentType.getContentType());
    }

    public void addCookies(String value) {
        responseCookies.add(SET_COOKIE + COLON + value);
    }

    public String getResponseHeaders() {
        return responseCookies.size() != EMPTY_RESPONSE_COOKIES ?
                responseHeaders.entrySet().stream()
                        .map(map -> map.getKey().getField() + COLON + map.getValue())
                        .collect(Collectors.joining(CRLF)) + CRLF + String.join(CRLF, responseCookies) :
                responseHeaders.entrySet().stream()
                        .map(map -> map.getKey().getField() + COLON + map.getValue())
                        .collect(Collectors.joining(CRLF));
    }
}
