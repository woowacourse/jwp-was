package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {

    private final Map<String, String> headers;

    public ResponseHeader() {
        this.headers = new HashMap<>();
    }

    public ResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
