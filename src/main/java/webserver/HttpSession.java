package webserver;

import java.util.Map;

public class HttpSession {

    private final String id;
    private final Map<String, String> attributes;
    private boolean valid;

    HttpSession(String id, Map<String, String> attributes) {
        this.id = id;
        this.attributes = attributes;
        valid = true;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public boolean isInvalid() {
        return !valid;
    }

    public void invalidate() {
        valid = false;
    }
}
