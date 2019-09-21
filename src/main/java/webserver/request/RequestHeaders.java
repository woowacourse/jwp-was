package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class RequestHeaders {

    private Map<String, String> headers;

    public RequestHeaders() {
        this.headers = new HashMap<>();
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }

    public String get(String key) {
        return headers.get(key);
    }

    public boolean containsKey(String key) {
        return headers.containsKey(key);
    }

    @Override
    public String toString() {
        return "RequestHeaders{" +
                "headers=" + headers +
                '}';
    }
}
