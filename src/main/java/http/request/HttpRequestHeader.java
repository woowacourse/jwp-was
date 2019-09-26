package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHeader.class);
    private static final String DELIMITER_OF_HEADER = ":";

    private final HttpRequestStartLine httpRequestStartLine;
    private final Map<String, String> headers = new HashMap<>();

    public HttpRequestHeader(final List<String> inputs) {
        httpRequestStartLine = new HttpRequestStartLine(inputs.get(0));
        for (String header : inputs.subList(1, inputs.size())) {
            int indexOfDelimiter = header.indexOf(DELIMITER_OF_HEADER);
            String key = header.substring(0, indexOfDelimiter).trim().toLowerCase();
            String value = header.substring(indexOfDelimiter + 1).trim();

            headers.put(key, value);
        }
    }

    public String getMethod() {
        return httpRequestStartLine.getMethod();
    }

    public String getResourcePath() {
        return httpRequestStartLine.getResourcePath();
    }

    public String getVersion() {
        return httpRequestStartLine.getVersion();
    }

    public String get(String key) {
        return headers.get(key.toLowerCase());
    }

    String getParameter(String key) {
        return httpRequestStartLine.getQueryParameter(key);
    }
}
