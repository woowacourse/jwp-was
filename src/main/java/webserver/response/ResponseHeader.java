package webserver.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHeader {
    private final Map<String, String> headers;

    public ResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static ResponseHeader emptyHeader() {
        return new ResponseHeader(new LinkedHashMap<>());
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, null);
    }
}
