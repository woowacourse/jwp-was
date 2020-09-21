package webserver.request;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import exception.InvalidRequestHeaderException;

public class RequestHeader {
    private static final int KEY_VALUE_LENGTH = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int DEFAULT_VALUE = 0;
    private static final String DELIMITER = ":";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> headers;

    private RequestHeader(final Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader of(String header) {
        try {
            String[] headers = header.split(System.lineSeparator());

            Map<String, String> attribute = new LinkedHashMap<>();
            for (String s : headers) {
                String[] map = s.split(DELIMITER, KEY_VALUE_LENGTH);
                attribute.put(map[KEY_INDEX], map[VALUE_INDEX].trim());
            }

            return new RequestHeader(attribute);
        } catch (Exception e) {
            throw new InvalidRequestHeaderException();
        }
    }

    public int getContentLength() {
        String contentLength = headers.get(CONTENT_LENGTH);

        if (Objects.isNull(contentLength) || contentLength.isEmpty()) {
            return DEFAULT_VALUE;
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
                .append(DELIMITER + " ")
                .append(entry.getValue())
                .append(System.lineSeparator());
        }

        return builder.toString();
    }
}
