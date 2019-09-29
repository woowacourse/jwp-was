package http.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class HttpHeader {
    private static final String DELIMITER_OF_HEADER = ":";
    private static final String DELIMITER_OF_HEADERS = "\r\n";

    private final Map<String, String> headers = new HashMap<>();

    public static HttpHeader empty() {
        return new HttpHeader();
    }

    private HttpHeader() {

    }

    public HttpHeader(final Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public void addHeader(final String name, final String value) {
        headers.put(name, value);
    }

    public String getHeader(final String name) {
        return headers.get(name);
    }

    public String parse() {
        return String.join("", parseHeaders());
    }

    private List<String> parseHeaders() {
        return this.headers.entrySet().stream()
                .map(entry -> entry.getKey() + DELIMITER_OF_HEADER + " " + entry.getValue() + DELIMITER_OF_HEADERS)
                .collect(toList());
    }
}
