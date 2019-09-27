package http.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeader {
    private static final String DELIMITER_OF_HEADER = ":";

    private final HttpStartLine httpStartLine;
    private final Map<String, String> headers = new HashMap<>();

    public HttpRequestHeader(final List<String> inputs) {
        httpStartLine = new HttpStartLine(inputs.get(0));
        for (String header : inputs.subList(1, inputs.size())) {
            int indexOfDelimiter = header.indexOf(DELIMITER_OF_HEADER);
            String key = header.substring(0, indexOfDelimiter).trim().toLowerCase();
            String value = header.substring(indexOfDelimiter + 1).trim();

            headers.put(key, value);
        }
    }

    public String getMethod() {
        return httpStartLine.getMethod();
    }

    public String getResourcePath() {
        return httpStartLine.getResourcePath();
    }

    public String getVersion() {
        return httpStartLine.getVersion();
    }

    public String get(String key) {
        return headers.get(key.toLowerCase());
    }

    String getParameter(String key) {
        return httpStartLine.getQueryParameter(key);
    }
}
