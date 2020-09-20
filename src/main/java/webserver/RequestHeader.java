package webserver;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import exception.InvalidRequestHeaderException;

public class RequestHeader {
    private final Map<String, String> headers;

    private RequestHeader(final Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader of(String header) {
        try {
            String[] headers = header.split("\n");

            Map<String, String> attribute = new LinkedHashMap<>();
            for (int i = 1; i < headers.length; i++) {
                String[] map = headers[i].split(":", 2);
                attribute.put(map[0], map[1].trim());
            }

            return new RequestHeader(attribute);
        } catch (Exception e) {
            throw new InvalidRequestHeaderException();
        }
    }

    public int getContentLength() {
        String contentLength = headers.get("Content-Length");

        if (Objects.isNull(contentLength) || contentLength.isEmpty()) {
            contentLength = "0";
        }
        return Integer.parseInt(contentLength);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public String getHeader(String header) {
        return headers.getOrDefault(header, null);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.append(entry.getKey())
                .append(": ")
                .append(entry.getValue())
                .append(System.lineSeparator());
        }

        return builder.toString();
    }
}
