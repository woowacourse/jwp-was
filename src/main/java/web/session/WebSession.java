package web.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebSession implements HttpSession {
    private final Map<String, Object> attributes = new HashMap<>();
    private UUID id = UUID.randomUUID();

    @Override
    public String getId() {
        return id.toString();
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
}
