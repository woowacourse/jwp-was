package webserver.http;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String DELIMITER = ":";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> httpHeaders;

    public HttpHeaders(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static HttpHeaders from(List<String> headers) {
        return headers.stream()
            .map(header -> header.split(DELIMITER))
            .collect(
                collectingAndThen(toMap(header -> header[KEY_INDEX], header -> header[VALUE_INDEX].trim()), HttpHeaders::new));
    }

    public int getContentLength() {
        return Integer.parseInt(httpHeaders.get(CONTENT_LENGTH));
    }

    public String get(String key) {
        return httpHeaders.get(key);
    }

    public Map<String, String> getHttpHeaders() {
        return Collections.unmodifiableMap(httpHeaders);
    }
}
