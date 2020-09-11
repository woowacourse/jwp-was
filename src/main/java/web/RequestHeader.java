package web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestHeader {
    private static final String DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final int INDEX_KEY = 0;
    private static final int INDEX_VALUE = 1;

    private final Map<String, String> headers;

    public RequestHeader(List<String> headers) {
        this.headers = headers.stream()
                .map(header -> header.split(DELIMITER))
                .collect(Collectors.toMap(it -> it[INDEX_KEY], it -> it[INDEX_VALUE]));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get(CONTENT_LENGTH));
    }
}
