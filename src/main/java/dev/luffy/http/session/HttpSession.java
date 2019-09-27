package dev.luffy.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession implements Session {

    private String id;
    private Map<String, Object> httpSession;

    HttpSession(String id) {
        this.id = id;
        this.httpSession = new HashMap<>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        httpSession.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return httpSession.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        httpSession.remove(name);
    }

    @Override
    public void invalidate() {
        httpSession.clear();
    }
}
