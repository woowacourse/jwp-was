package http;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeaders {
    private static final String HEADER_KEY_VALUE_DELIMITER = ": ";
    private static final String CONTENT_TYPE_POSTFIX = ";charset=utf-8";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "Location";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> headers;

    private HttpHeaders(Map<String, String> headers) {
        this.headers = Objects.requireNonNull(headers, "request headers가 존재하지 않습니다.");
    }

    public static HttpHeaders from(final List<String> headers) {
        return headers.stream()
                .map(header -> header.split(HEADER_KEY_VALUE_DELIMITER))
                .collect(collectingAndThen(toMap(header -> header[0], header -> header[1]), HttpHeaders::new));
    }

    public static HttpHeaders empty() {
        return new HttpHeaders(new HashMap<>());
    }

    public String toMessage(final String postfix) {
        return headers.entrySet().stream()
                .map(header -> header.getKey() + HEADER_KEY_VALUE_DELIMITER + header.getValue())
                .collect(Collectors.joining(postfix));
    }

    public boolean hasContentLength() {
        return headers.containsKey(CONTENT_LENGTH);
    }

    public void setContentType(final String value) {
        headers.put(CONTENT_TYPE, value + CONTENT_TYPE_POSTFIX);
    }

    public void setLocation(final String value) {
        headers.put(LOCATION, value);
    }

    public void setHeader(final String key, final String value) {
        headers.put(key, value);
    }

    public String getHeader(final String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader(CONTENT_LENGTH));
    }

    public void setContentLength(final int value) {
        headers.put(CONTENT_LENGTH, String.valueOf(value));
    }

    @Override
    public String toString() {
        return "RequestHeaders{" +
                "headers=" + headers +
                '}';
    }
}
