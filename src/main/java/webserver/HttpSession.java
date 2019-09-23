package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private final String id;
    private final Map<String, String> attributes;
    private boolean validity;

    private HttpSession(String id, Map<String, String> attributes) {
        this.id = id;
        this.attributes = attributes;
        validity = true;
    }

    public static HttpSession createSession() {
        return new HttpSession(UUID.randomUUID().toString(), new HashMap<>());
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
        return !validity;
    }

    public void invalidate() {
        validity = false;
    }
}
