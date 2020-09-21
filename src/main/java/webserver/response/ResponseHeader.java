package webserver.response;

import java.util.Map;

public class ResponseHeader {
    private final Map<String, String> headers;

    public ResponseHeader(final Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, null);
    }
}
