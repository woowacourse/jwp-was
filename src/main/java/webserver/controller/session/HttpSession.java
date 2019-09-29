package webserver.controller.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private Map<String, Object> attributes;
    private final UUID uuid;
    private HttpSessionManager httpSessionManager;

    public HttpSession(UUID uuid) {
        this.attributes = new HashMap<>();
        this.uuid = uuid;
        this.httpSessionManager = HttpSessionManager.getInstance();
        httpSessionManager.addSession(uuid, this);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setAttributes(String name, Object value) {
        attributes.put(name,value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribue(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
        this.httpSessionManager.removeSession(this.uuid);
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }
}
