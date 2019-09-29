package http;

import exception.SessionInvalidException;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private String id;
    private boolean invalid;
    private Map<String, Object> attribute;

    public Session(String id) {
        attribute = new HashMap<>();
        this.id = id;
        this.invalid = false;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        isSessionInvalid();
        attribute.put(name, value);
    }

    public Object getAttribute(String name) {
        isSessionInvalid();
        return attribute.get(name);
    }

    public void removeAttribute(String name) {
        isSessionInvalid();
        attribute.remove(name);
    }

    public void invalidate() {
        this.invalid = true;
    }

    public boolean isInvalidTrue() {
        return invalid;
    }

    private void isSessionInvalid() {
        if (invalid) {
            throw new SessionInvalidException();
        }
    }
}
