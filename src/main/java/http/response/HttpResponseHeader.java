package http.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponseHeader {

    private final Map<String, Object> responseHeader;

    public HttpResponseHeader() {
        this.responseHeader = new HashMap<>();
    }

    public void addResponseHeader(String key, Object value) {
        this.responseHeader.put(key, value);
    }

    public Object getValue(final String key) {
        return this.responseHeader.get(key);
    }

    public Set<String> keySet() {
        return this.responseHeader.keySet();
    }
}
