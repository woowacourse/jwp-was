package webserver.http;

import webserver.http.exception.NonexistentSessionValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private String id;
    private Map<String,Object> values;

    public HttpSession() {
        this.id = UUID.randomUUID().toString();
        this.values = new HashMap<>();
    }

    public void setAttribute(String key, Object value) {
        values.put(key, value);
    }

    public void removeAttribute(String key) {
        checkExistValue(key);
        values.remove(key);
    }

    private void checkExistValue(String key) {
        if (!values.containsKey(key)) {
            throw new NonexistentSessionValue();
        }
    }

    public void invalidate() {
        values.clear();
    }

    public Object getAttribute(String key) {
        checkExistValue(key);
        return values.get(key);
    }

    public String getId() {
        return id;
    }
}
