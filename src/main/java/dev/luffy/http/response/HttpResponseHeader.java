package dev.luffy.http.response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponseHeader {

    private static final String HEADER_DELIMITER = "\r\n";

    private Map<String, String> headers;

    public HttpResponseHeader() {
        this.headers = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String combine() {
        String combined = headers.keySet().stream()
                .map(key -> String.format("%s: %s", key, headers.get(key)))
                .collect(Collectors.joining(HEADER_DELIMITER));
        return combined + HEADER_DELIMITER;
    }
}
