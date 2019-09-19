package http;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpRequestHeader {
    private static final String HEADER_SPLITTER = ": ";

    private final Map<String, String> headers;

    private HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpRequestHeader of(List<String> headerLines) {
        Map<String, String> headers = headerLines.stream()
                .collect(Collectors.toMap(
                        headerLine -> headerLine.split(HEADER_SPLITTER)[0],
                        headerLine -> headerLine.split(HEADER_SPLITTER)[1]
                ));

        return new HttpRequestHeader(headers);
    }

    public String getHeader(String key) {
        return Optional.ofNullable(headers.get(key))
                .orElseThrow(() -> new NotFoundHttpRequestHeader(key));
    }
}
