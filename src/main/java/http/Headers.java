package http;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

public class Headers {
    private static final String DELIMITER = ": ";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> httpHeaders;

    private Headers(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static Headers from(List<String> httpHeaders) {
        return httpHeaders.stream()
                .map(line -> line.split(DELIMITER))
                .collect(
                        collectingAndThen(
                                toMap(
                                        token -> token[KEY],
                                        token -> token[VALUE]),
                                Headers::new)
                );
    }

    public boolean contains(String attribute) {
        return httpHeaders.containsKey(attribute);
    }

    public String getAttribute(String attribute) {
        return httpHeaders.get(attribute);
    }
}
