package webserver.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpSessions implements Map<String, HttpSession> {
    private Map<String, HttpSession> sessions;

    public HttpSessions() {
        sessions = new HashMap<>();
    }

    @Override
    public int size() {
        return sessions.size();
    }

    @Override
    public boolean isEmpty() {
        return sessions.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return sessions.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return sessions.containsValue(value);
    }

    @Override
    public HttpSession get(Object key) {
        return sessions.get(key);
    }

    @Override
    public HttpSession put(String key, HttpSession value) {
        return sessions.put(key, value);
    }

    @Override
    public HttpSession remove(Object key) {
        return sessions.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends HttpSession> m) {
        sessions.putAll(m);
    }

    @Override
    public void clear() {
        sessions.clear();
    }

    @Override
    public Set<String> keySet() {
        return sessions.keySet();
    }

    @Override
    public Collection<HttpSession> values() {
        return sessions.values();
    }

    @Override
    public Set<Entry<String, HttpSession>> entrySet() {
        return sessions.entrySet();
    }
}
