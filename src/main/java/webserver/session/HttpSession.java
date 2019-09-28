package webserver.session;

import java.util.Map;

public class HttpSession {
    private final String id;
    private Map<String, Object> attributes;

    public HttpSession(String id) {
        this.id = id;
    }

    public void setAttribute(String name, Object attribute) {
        attributes.put(name, attribute);
    }

    public void getAttribute(String name) {
        attributes.get(name);
    }
}
