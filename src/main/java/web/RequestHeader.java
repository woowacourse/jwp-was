package web;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestHeader {
    private static final String DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> headers;

    public RequestHeader(List<String> headers) {
        this.headers = headers.stream()
                .map(header -> header.split(DELIMITER))
                .collect(Collectors.toMap(it -> it[0], it -> it[1]));
    }

    public int getContentLength() {
        try {
            return Integer.parseInt(headers.get(CONTENT_LENGTH));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHeader that = (RequestHeader) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
