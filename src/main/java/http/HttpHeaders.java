package http;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class HttpHeaders {
    private Map<String, String> headers;

    public HttpHeaders() {
        headers = new LinkedHashMap<>();
    }

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String put(String key, String value) {
        return headers.put(key, value);
    }

    public boolean hasContentLength() {
        return headers.get("Content-Length") != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String key : headers.keySet()) {
            sb.append(key).append(": ").append(headers.get(key)).append("\r\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHeaders that = (HttpHeaders) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
