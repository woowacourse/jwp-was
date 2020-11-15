package http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

public class HttpHeaders {
    private static final HttpHeaders DEFAULT_HEADERS = new HttpHeaders(Collections.unmodifiableMap(new HashMap<>()));
    private static final String HTTP_HEADER_KEY_VALUE_SPLITTER = ": ";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final Integer DEFAULT_INT_VALUE = 0;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> headers;

    public static HttpHeaders from(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
            return DEFAULT_HEADERS;
        }
        Map<String, String> headers = Arrays.stream(input.split(System.lineSeparator()))
            .map(line -> line.split(HTTP_HEADER_KEY_VALUE_SPLITTER))
            .collect(Collectors.toMap(
                pair -> pair[KEY_INDEX], pair -> Objects.isNull(pair[VALUE_INDEX]) ? null : pair[VALUE_INDEX]
            ));
        return new HttpHeaders(headers);
    }

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Nullable
    public String getHeader(String name) {
        return headers.get(name);
    }

    public int getContentLength() {
        String contentLength = headers.get(CONTENT_LENGTH_KEY);

        if (Objects.isNull(contentLength) || contentLength.isEmpty()) {
            return DEFAULT_INT_VALUE;
        }

        return Integer.valueOf(contentLength);
    }

    public int size() {
        return headers.size();
    }
}
