package http;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

public class RequestHeaders {
    private static final String HEADER_KEY_VALUE_DELIMITER = ": ";

    private final Map<String, String> headers;

    public RequestHeaders(Map<String, String> headers) {
        this.headers = headers;
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
