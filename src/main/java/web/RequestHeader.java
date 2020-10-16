package web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {
    private static final String TOKEN_DELIMITER = ": ";

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader(List<String> lines) {
        for (String line : lines) {
            String[] tokens = line.split(TOKEN_DELIMITER);
            headers.put(tokens[0], tokens[1]);
        }
    }

    private boolean isEmpty(String line) {
        return line == null || "".equals(line);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        String contentLength = headers.get("Content-Length");
        return Integer.parseInt(contentLength);
    }
}
