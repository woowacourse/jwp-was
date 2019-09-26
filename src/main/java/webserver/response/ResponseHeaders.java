package webserver.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResponseHeaders {

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
}
