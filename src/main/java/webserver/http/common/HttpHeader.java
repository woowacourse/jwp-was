package webserver.http.common;

import webserver.http.common.exception.InvalidHeaderLines;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeader {
    private static final String HEADER_LINE_DELIMITER = ": ";
    private static final int HEADER_LENGTH = 2;

    private Map<String, String> headers;

    private HttpHeader(final Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    public static HttpHeader of(final List<String> headerLines) {
        validHeaderLines(headerLines);
        return new HttpHeader(init(headerLines));
    }

    private static void validHeaderLines(final List<String> headerLines) {
        if (headerLines == null) {
            throw new InvalidHeaderLines();
        }
    }

    private static Map<String, String> init(final List<String> headerLines) {
        if (headerLines.size() == 0) {
            return Collections.emptyMap();
        }

        return headerLines.stream()
                .map(line -> line.split(HEADER_LINE_DELIMITER))
                .filter(HttpHeader::correctLength)
                .collect(Collectors.toMap(token -> token[0], token -> token[1]));
    }

    private static boolean correctLength(String[] tokens) {
        return tokens.length == HEADER_LENGTH;
    }

    public String get(final String key) {
        return headers.get(key);
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
