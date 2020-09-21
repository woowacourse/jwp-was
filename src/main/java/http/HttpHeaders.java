package http;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {
    private static final String DELIMITER = ": ";
    private static final String CONCAT_VALUE = ", ";
    private static final String CRLF = "\r\n";

    private final Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders create() {
        return new HttpHeaders(new HashMap<>());
    }

    public void set(String key, String value) {
        if (headers.containsKey(key)) {
            headers.put(key, headers.get(key) + CONCAT_VALUE + value);
            return;
        }
        headers.put(key, value);
    }

    public String build() {
        return headers.entrySet()
            .stream()
            .map(it -> it.getKey() + DELIMITER + it.getValue())
            .collect(Collectors.joining(CRLF));
    }
}
