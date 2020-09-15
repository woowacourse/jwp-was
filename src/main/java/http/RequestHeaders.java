package http;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RequestHeaders {
    private static final String HEADER_KEY_VALUE_DELIMITER = ": ";

    private final Map<String, String> headers;

    private RequestHeaders(Map<String, String> headers) {
        this.headers = Objects.requireNonNull(headers, "request headers가 존재하지 않습니다.");
    }

    public static RequestHeaders from(final List<String> headers) {
        return headers.stream()
                .map(header -> header.split(HEADER_KEY_VALUE_DELIMITER))
                .collect(collectingAndThen(toMap(header -> header[0], header -> header[1]), RequestHeaders::new));
    }

    public String getHeader(final String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
