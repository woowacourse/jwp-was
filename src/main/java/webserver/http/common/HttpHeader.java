package webserver.http.common;

import webserver.http.common.exception.InvalidHeaderLines;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeader {
    private static final String HEADER_LINE_DELIMITER = ": ";
    private static final int HEADER_LENGTH = 2;
    private static final String COOKIE = "Cookie";
    private static final String LOGINED_TRUE = "logined=true";

    private Map<String, String> headers;

    // Response 관련 객체가 사용
    public HttpHeader() {
        headers = new HashMap<>();
    }

    // Request 관련 객체가 사용
    public HttpHeader(final List<String> headerLines) {
        validHeaderLines(headerLines);
        headers = parse(headerLines);
    }

    private void validHeaderLines(final List<String> headerLines) {
        if (headerLines == null) {
            throw new InvalidHeaderLines();
        }
    }

    private Map<String, String> parse(final List<String> headerLines) {
        if (headerLines.size() == 0) {
            return Collections.emptyMap();
        }

        return headerLines.stream()
                .map(line -> line.split(HEADER_LINE_DELIMITER))
                .filter(this::correctLength)
                .collect(Collectors.toMap(token -> token[0], token -> token[1]));
    }

    private boolean correctLength(String[] tokens) {
        return tokens.length == HEADER_LENGTH;
    }

    public String get(final String key) {
        return headers.get(key);
    }

    public boolean hasLoginCookie() {
        return get(COOKIE).contains(LOGINED_TRUE);
    }

    public void put(final String key, final String value) {
        headers.put(key, value);
    }

    public void remove(final String key) {
        headers.remove(key);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "headers=" + headers +
                '}';
    }
}
