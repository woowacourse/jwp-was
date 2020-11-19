package dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Headers {

    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;
    private static final String EMPTY = "";

    private final Map<String, String> headers;

    public Headers(Map<String, String> headers) {
        this.headers = headers;
    }

    public static Headers from(List<String> headers) {
        return new Headers(makeHeaders(headers));
    }

    private static Map<String, String> makeHeaders(List<String> headers) {
        return headers.stream()
            .filter(header -> header.contains(HEADER_DELIMITER))
            .map(header -> header.split(HEADER_DELIMITER))
            .collect(
                Collectors.toMap(
                    header -> header[HEADER_KEY_INDEX],
                    Headers::makeHeaderValue,
                    (p1, p2) -> p1
                )
            );
    }

    private static String makeHeaderValue(String[] header) {
        if (header.length == HEADER_VALUE_INDEX) {
            return EMPTY;
        }
        return header[HEADER_VALUE_INDEX];
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String get(String header) {
        return headers.keySet().stream()
            .filter(key -> key.equalsIgnoreCase(header))
            .map(headers::get)
            .findFirst()
            .orElse(null);
    }

    @Override
    public String toString() {
        return "Headers{" +
            "headers=" + headers +
            '}';
    }
}
