package http;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleHttpSession implements HttpSession {
    private static Map<String, HttpSession> HTTP_SESSION_STORAGE = new ConcurrentHashMap<>();

    private String id;
    private Map<String, Object> attributes;
    private boolean isNew;

    public static HttpSession getHttpSessionStorage(String sessionId) {
        return HTTP_SESSION_STORAGE.getOrDefault(sessionId, new SimpleHttpSession());
    }

    private SimpleHttpSession() {
        this.id = UUID.randomUUID().toString();
        this.attributes = new ConcurrentHashMap<>();
        this.isNew = true;
        HTTP_SESSION_STORAGE.put(id, this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        attributes.clear();
    }

    @Override
    public boolean isNew(){
        return isNew;
    }

    @Override
    public void toOld(){
        this.isNew = false;
    }
}
