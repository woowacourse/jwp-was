package http.common;

import http.exception.NotExistSessionValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private String id;
    private Map<String, Object> values = new HashMap<>();

    public HttpSession() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(String name) {
        checkExistValue(name);
        return values.get(name);
    }

    public void setAttribute(String name, Object value) {
        values.put(name, value);
    }

    public void removeAttribute(String name) {
        checkExistValue(name);
        values.remove(name);
    }

    private void checkExistValue(String name) {
        if (!values.containsKey(name)) {
            throw new NotExistSessionValue("존재하지 않는 value입니다.");
        }
    }

    public void invalidate() {
        values = new HashMap<>();
    }
}
