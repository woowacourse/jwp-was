package webserver.http.response.core;

import webserver.http.HttpHeaderField;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {
    private static final String CRLF = "\r\n";
    private static final String COLON = ": ";
    private final Map<HttpHeaderField, String> responseHeaders = new HashMap<>();

    public void addHeaders(HttpHeaderField httpHeaderField, String value) {
        responseHeaders.put(httpHeaderField, value);
    }

    public void addHeaders(HttpHeaderField httpHeaderField, ResponseContentType contentType) {
        responseHeaders.put(httpHeaderField, contentType.getContentType());
    }

    public boolean hasResponseField(final String field) {
        return responseHeaders.keySet().stream()
                .anyMatch(key -> key.getField().equals(field));
    }

    public String getResponseHeaders() {
        return responseHeaders.entrySet().stream()
                .map(map -> map.getKey().getField() + COLON + map.getValue())
                .collect(Collectors.joining(CRLF));
    }
}
