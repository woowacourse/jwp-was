package session;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private String id;
    private Map<String, Object> session = new HashMap<>();

    public Session(String sessionId) {
        this.id = sessionId;
    }

    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    public Object getAttribute(String name) {
        validateAttributeIsExist(name);
        return session.get(name);
    }

    public void removeAttribute(String name) {
        validateAttributeIsExist(name);
        session.remove(name);
    }

    private void validateAttributeIsExist(String name) {
        if (!session.containsKey(name)) {
            throw new IllegalArgumentException("session \"" + name + "\" is not exist.");
        }
    }

    public void invalidate() {
        session.clear();
    }

    public String getId() {
        return id;
    }

    public boolean isInvalid() {
        return session.isEmpty();
    }
}
