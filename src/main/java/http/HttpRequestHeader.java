package http;

import http.request.exception.NotFoundHttpRequestHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HttpRequestHeader {
    private static final String HEADER_SPLITTER = ": ";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> headers = new HashMap<>();

    public HttpRequestHeader() {
    }

    public HttpRequestHeader create(List<String> headerLines) {
        for (String line : headerLines) {
            String[] splitLine = line.split(HEADER_SPLITTER);
            headers.put(splitLine[KEY], splitLine[VALUE]);
        }
        return this;
    }

    public String getHeader(String key) {
        return Optional.ofNullable(headers.get(key))
                .orElseThrow(() -> new NotFoundHttpRequestHeader(key));
    }

    public boolean contains(String key) {
        return headers.containsKey(key);
    }
}
