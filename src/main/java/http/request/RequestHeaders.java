package http.request;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

public class RequestHeaders {
    private static final String DELIMITER = ": ";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> requestHeaders;

    private RequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public static RequestHeaders from(List<String> requestHeaders) {
        return requestHeaders.stream()
                .map(line -> line.split(DELIMITER))
                .collect(
                        collectingAndThen(
                                toMap(
                                        token -> token[KEY],
                                        token -> token[VALUE]),
                                RequestHeaders::new)
                );
    }

    public boolean contains(String attribute) {
        return requestHeaders.containsKey(attribute);
    }

    public String getAttribute(String attribute) {
        return requestHeaders.get(attribute);
    }
}
