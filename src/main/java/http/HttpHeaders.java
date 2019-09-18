package http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private static final String HEADER_DELIMITER = ":\\s+";

    private Map<String, String> headers;

    public HttpHeaders(List<String> lines) {
        this.headers = splitHeaders(lines);
    }

    private Map<String, String> splitHeaders(List<String> lines) {
        Map<String, String> headers = new HashMap<>();
        for (String header : lines) {
            String[] splicedHeader = header.split(HEADER_DELIMITER);
            headers.put(splicedHeader[KEY_INDEX], splicedHeader[VALUE_INDEX]);
        }
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public boolean hasContentLength() {
        return headers.get("Content-Length") != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String key : headers.keySet()) {
            sb.append(key).append(": ").append(headers.get(key)).append("\n");
        }
        return sb.toString();
    }
}
