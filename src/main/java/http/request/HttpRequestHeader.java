package http.request;

import http.request.exception.NotFoundHttpRequestHeader;

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
                .map(headerLine -> headerLine.split(HEADER_SPLITTER))
                .filter(splittedStrings -> 2 <= splittedStrings.length)
                .collect(Collectors.toMap(
                        splittedStrings -> splittedStrings[0].toLowerCase(),
                        splittedStrings -> splittedStrings[1]
                ));

        return new HttpRequestHeader(headers);
    }

    public String getHeader(String key) {
        return Optional.ofNullable(headers.get(key.toLowerCase()))
                .orElseThrow(() -> new NotFoundHttpRequestHeader(key));
    }

    public boolean contains(String key) {
        return headers.containsKey(key.toLowerCase());
    }
}
