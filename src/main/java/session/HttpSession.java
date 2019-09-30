package session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    public static final String SESSION_ID = "SessionId";

    private final Map<String, Object> info = new HashMap<>();
    private final String id;

    HttpSession(String uuid) {
        this.id = uuid;
    }


    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        info.put(name, value);
    }

    public Object getAttributes(String name) {
        return info.get(name);
    }

    public void removeAttribute(String name) {
        info.remove(name);
    }

    public void invalidate() {
        info.clear();
    }

}
