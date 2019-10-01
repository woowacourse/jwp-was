package dev.luffy.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession implements Session {

    private String id;
    private Map<String, Object> sessionAttributes;

    HttpSession(String id) {
        this.id = id;
        this.sessionAttributes = new HashMap<>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return sessionAttributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        sessionAttributes.remove(name);
    }

    @Override
    public void invalidate() {
        sessionAttributes.clear();
    }
}
