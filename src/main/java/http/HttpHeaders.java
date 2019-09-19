package http;

import java.util.Map;

public class HttpHeaders {
    private Map<String, String> headers;

    HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    boolean hasContentLength() {
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
