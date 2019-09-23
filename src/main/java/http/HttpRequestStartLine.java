package http;

import java.util.Arrays;
import java.util.List;

public class HttpRequestStartLine {
    private static final String DELIMITER = " ";
    private static final int INDEX_OF_METHOD = 0;
    private static final int INDEX_OF_URL = 1;
    private static final int INDEX_OF_VERSION = 2;

    private final List<String> httpRequestLine;

    public HttpRequestStartLine(String httpRequestLine) {
        this.httpRequestLine = Arrays.asList(httpRequestLine.split(DELIMITER));
    }

    String getMethod() {
        return httpRequestLine.get(INDEX_OF_METHOD);
    }

    String getUrl() {
        return httpRequestLine.get(INDEX_OF_URL);
    }

    String getVersion() {
        return httpRequestLine.get(INDEX_OF_VERSION);
    }
}
