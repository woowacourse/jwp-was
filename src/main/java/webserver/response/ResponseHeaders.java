package webserver.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResponseHeaders {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";

    private Map<String, Object> headers;

    public ResponseHeaders() {
        this.headers = new HashMap<>();
    }

    public void put(String key, Object value) {
        this.headers.put(key, value);
    }

    public Object get(String key) {
        return this.headers.get(key);
    }

    public Set<String> keySet() {
        return this.headers.keySet();
    }

    @Override
    public String toString() {
        return "ResponseHeaders{" +
                "headers=" + headers +
                '}';
    }
}
